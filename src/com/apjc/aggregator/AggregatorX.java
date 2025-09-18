package com.apjc.aggregator;

import com.apjc.aggregator.instructions.Instructions;
import com.apjc.aggregator.instructions.InstructionsFilter;
import com.apjc.aggregator.function.Calculator;
import com.apjc.aggregator.function.Filter;

public class AggregatorX <T, U> extends AggregatorFunctional<T, AggregatorX.Result<U>> {
	
	public AggregatorX(Instructions<? super T, U> ins1, Instructions<? super T, U> ins2) {
		super(new InstructionsContainer<>(ins1, ins2));
	}
	
	public AggregatorX(Instructions<? super T, U> ins1, Instructions<? super T, U> ins2, Filter<T> fil) {
		super(new InstructionsFilter<>(new InstructionsContainer<>(ins1, ins2)) {
			
			@Override
			public boolean filter(T values) {
				return fil.filter(values);
			}
			
		});
	}
	
	public AggregatorX(InstructionsFilter<? super T, U> ins1, Instructions<? super T, U> ins2) {
		super(new InstructionsFilter<>(new InstructionsContainer<>(ins1, ins2)) {
			
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
		
		private Result() {
			values1 = null;
			values2 = null;
		}
		
		private Result(N val1, N val2) {
			values1 = val1;
			values2 = val2;
		}
		
		public N applu(Calculator<N> cal) {
			return cal.calculate(values1, values2);
		}
		
	}
	
	private static class InstructionsContainer<E, N> implements Instructions<E, Result<N>>{
		
		private Instructions<? super E, N> instructions1;
		private Instructions<? super E, N> instructions2;
		
		public InstructionsContainer(Instructions<? super E, N> ins1, Instructions<? super E, N> ins2) {
			instructions1 = ins1;
			instructions2 = ins2;
		}

		@Override
		public Result<N> applu(Result<N> result, E values) {
			Result<N> data = result != null ? result : new Result<>();
			N val1 = instructions1.applu(data.values1, values);
			N val2 = instructions2.applu(data.values2, values);
			return new Result<>(val1, val2);
		}

		@Override
		public Aggregator<E, Result<N>> getAggregator() {
			return new AggregatorX<>(instructions1, instructions2);
		}
		
	}

}
