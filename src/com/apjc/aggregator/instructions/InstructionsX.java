package com.apjc.aggregator.instructions;

import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.AggregatorFunctional;
import com.apjc.aggregator.function.Calculator;

public class InstructionsX<T, U> implements Instructions<T, InstructionsX.Result<U>> {

	private final Instructions<? super T, U> INSTRUCTIONS1;
	private final Instructions<? super T, U> INSTRUCTIONS2;
	
	public InstructionsX(Instructions<? super T, U> instructions1, Instructions<? super T, U> instructions2) {
		INSTRUCTIONS1 = instructions1;
		INSTRUCTIONS2 = instructions2;
	}
	
	@Override
	public Result<U> applu(Result<U> result, T values) {
		Result<U> data = result != null ? result : new Result<>(null, null);
		U val1 = INSTRUCTIONS1.applu(data.values1, values);
		U val2 = INSTRUCTIONS2.applu(data.values2, values);
		return new Result<>(val1, val2);
	}

	@Override
	public Aggregator<T, Result<U>> getAggregator() {
		return new AggregatorFunctional<>(this);
	}
	
	public static class Result<N>{
		
		private N values1;
		private N values2;
		
		private Result(N values1, N values2) {
			this.values1 = values1;
			this.values2 = values2;
		}
		
		public N applu(Calculator<N> cal) {
			return cal.calculate(values1, values2);
		}
		
	}
	
}
