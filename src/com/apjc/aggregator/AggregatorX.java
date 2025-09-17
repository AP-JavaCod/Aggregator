package com.apjc.aggregator;

import com.apjc.aggregator.instructions.*;
import com.apjc.aggregator.function.Calculator;
import com.apjc.aggregator.function.Filter;

public class AggregatorX <T, U> extends AggregatorFunctional<T, AggregatorX.Result<U>> {
	
	public AggregatorX(Instructions<? super T, U> ins1, Instructions<? super T, U> ins2) {
		super(new InstructionsContainer<>(ins1, ins2));
	}
	
	public AggregatorX(Instructions<? super T, U> ins1, Instructions<? super T, U> ins2, Filter<T> fil) {
		super(new InstructionsContainerFilter<>(ins1, ins2) {
			
			@Override
			public boolean filter(T values) {
				return fil.filter(values);
			}
			
		});
	}
	
	public AggregatorX(InstructionsFilter<? super T, U> ins1, Instructions<? super T, U> ins2) {
		super(new InstructionsContainerFilter<>(ins1, ins2) {
			
			@Override
			public boolean filter(T values) {
				return ins1.filter(values);
			}
			
		});
	}
	
	public U aggregation(T[] values, Calculator<U> cal) {
		return aggregation(values).applu(cal);
	}
	
	public static class Result<N>{
		
		private N values1;
		private N values2;
		
		private Result(N val1, N val2) {
			values1 = val1;
			values2 = val2;
		}
		
		public N applu(Calculator<N> cal) {
			return cal.calculate(values1, values2);
		}
		
	}
	
	private static class InstructionsContainer<E, N> extends InstructionsFunctional<E, Result<N>>{
		
		private Instructions<? super E, N> instructions1;
		private Instructions<? super E, N> instructions2;
		
		public InstructionsContainer(Instructions<? super E, N> ins1, Instructions<? super E, N> ins2) {
			instructions1 = ins1;
			instructions2 = ins2;
		}

		@Override
		public AggregatorX.Result<N> calculate(AggregatorX.Result<N> val1, AggregatorX.Result<N> val2) {
			N result1 = instructions1.calculate(val1.values1,  val2.values1);
			N result2 = instructions2.calculate(val1.values2, val2.values2);
			return new Result<>(result1, result2);
		}

		@Override
		public AggregatorX.Result<N> transform(E values) {
			N result1 = instructions1.transform(values);
			N result2 = instructions2.transform(values);
			return new Result<>(result1, result2);
		}
		
	}
	
	private abstract static class InstructionsContainerFilter<E, N> extends InstructionsContainer<E, N> implements Filter<E>{

		public InstructionsContainerFilter(Instructions<? super E, N> ins1, Instructions<? super E, N> ins2) {
			super(ins1, ins2);
		}

		@Override
		public Result<N> applu(Result<N> result, E values) {
			return filter(values) ? super.applu(result, values) : result;
		}
		
	}

}
