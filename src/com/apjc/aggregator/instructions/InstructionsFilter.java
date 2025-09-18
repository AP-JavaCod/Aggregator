package com.apjc.aggregator.instructions;

import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.AggregatorFunctional;
import com.apjc.aggregator.function.Filter;

public abstract class InstructionsFilter <T, U> implements Instructions<T, U>, Filter<T> {
	
	private final Instructions<T, U> INSTRUCTIONS;
	
	public InstructionsFilter(Instructions<T, U> instructions) {
		INSTRUCTIONS = instructions;
	}
	
	@Override
	public U applu(U result, T values) {
		return filter(values) ? INSTRUCTIONS.applu(result, values) : result;
	}
	
	public Aggregator<T, U> getAggregator(){
		return new AggregatorFunctional<>(this);
	}

}
