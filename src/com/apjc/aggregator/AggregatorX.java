package com.apjc.aggregator;

import com.apjc.aggregator.instructions.InstructionsX;
import com.apjc.aggregator.function.Calculator;

public class AggregatorX<T, U> extends ContainerInstructionsX<T, U> implements Aggregator<T, U>{
	
	public AggregatorX(InstructionsX<T, U> instructions, Calculator<U> apply) {
		super(instructions, apply);
	}
	
	@Override
	public U aggregation(T[] values) {
		InstructionsX.Result<U> result = null;
		for(T el : values) {
			result = instructionsApply(result, el);
		}
		return calculate(result);
	}

}
