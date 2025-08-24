package com.apjc.aggregator;

import com.apjc.aggregator.instructions.*;
import com.apjc.aggregator.function.Filter;

public abstract class AggregatorX <T, U> implements Aggregator<T, U> {
	
	private final InstructionsContainer INSTRUCTIONS;
	
	public AggregatorX(Instructions<? super T, U> ins1, Instructions<? super T, U> ins2) {
		INSTRUCTIONS = new InstructionsContainer(ins1, ins2);
	}
	
	public AggregatorX(Instructions<? super T, U> ins1, Instructions<? super T, U> ins2, Filter<T> fil) {
		INSTRUCTIONS = new InstructionsContainerFilter(ins1, ins2) {
			
			@Override
			public boolean filter(T values) {
				return fil.filter(values);
			}
			
		};
	}
	
	public AggregatorX(InstructionsFilter<? super T, U> ins1, Instructions<? super T, U> ins2) {
		INSTRUCTIONS = new InstructionsContainerFilter(ins1, ins2) {
			
			@Override
			public boolean filter(T values) {
				return ins1.filter(values);
			}
			
		};
	}

	@Override
	public U aggregation(T[] values) {
		Result result = null;
		for(T el : values) {
			if(el == null) {
				continue;
			}
			result = INSTRUCTIONS.applu(result, el);
		}
		return getResult(result.values1, result.values2);
	}
	
	protected abstract U getResult(U val1, U val2);
	
	private class Result{
		
		private U values1;
		private U values2;
		
		public Result(U val1, U val2) {
			values1 = val1;
			values2 = val2;
		}
		
	}
	
	private class InstructionsContainer extends InstructionsFunctional<T, Result>{
		
		private Instructions<? super T, U> instructions1;
		private Instructions<? super T, U> instructions2;
		
		public InstructionsContainer(Instructions<? super T, U> ins1, Instructions<? super T, U> ins2) {
			instructions1 = ins1;
			instructions2 = ins2;
		}

		@Override
		public AggregatorX<T, U>.Result calculate(AggregatorX<T, U>.Result val1, AggregatorX<T, U>.Result val2) {
			U result1 = instructions1.calculate(val1.values1,  val2.values1);
			U result2 = instructions2.calculate(val1.values2, val2.values2);
			return new Result(result1, result2);
		}

		@Override
		public AggregatorX<T, U>.Result transform(T values) {
			U result1 = instructions1.transform(values);
			U result2 = instructions2.transform(values);
			return new Result(result1, result2);
		}
		
	}
	
	private abstract class InstructionsContainerFilter extends InstructionsContainer implements Filter<T>{

		public InstructionsContainerFilter(Instructions<? super T, U> ins1, Instructions<? super T, U> ins2) {
			super(ins1, ins2);
		}

		@Override
		public Result applu(Result result, T values) {
			return filter(values) ? super.applu(result, values) : result;
		}
		
	}

}
