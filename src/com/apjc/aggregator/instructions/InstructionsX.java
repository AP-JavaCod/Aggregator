package com.apjc.aggregator.instructions;

import com.apjc.aggregator.ContainerInstructions;
import com.apjc.aggregator.function.Calculator;

public class InstructionsX<T, U> extends ContainerInstructions<T, InstructionsX.Result<U>> implements Instructions<T, InstructionsX.Result<U>> {
	
	public InstructionsX(Instructions<? super T, U> instructions1, Instructions<? super T, U> instructions2) {
		super((res, val) -> {
			Result<U> data = res != null ? res : new Result<>(null, null);
			U val1 = instructions1.applu(data.values1, val);
			U val2 = instructions2.applu(data.values2, val);
			return new Result<>(val1, val2);
		});
	}
	
	@Override
	public Result<U> applu(Result<U> result, T values) {
		return instructionsApply(result, values);
	}
	
	public static class Result<N>{
		
		private final N values1;
		private final N values2;
		
		private Result(N values1, N values2) {
			this.values1 = values1;
			this.values2 = values2;
		}
		
		public N applu(Calculator<N> cal) {
			return cal.calculate(values1, values2);
		}
		
	}
	
}
