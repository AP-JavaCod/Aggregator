package com.apjc.aggregator.instructions;

import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.AggregatorFunctional;
import com.apjc.aggregator.ContainerInstructions;
import com.apjc.aggregator.function.Filter;

public abstract class InstructionsFilter <T, U> extends ContainerInstructions<T, U> implements Instructions<T, U>, Filter<T> {
		
	public InstructionsFilter(Instructions<? super T, U> instructions) {
		super(instructions);
	}
	
	@Override
	public U applu(U result, T values) {
		return values != null && filter(values) ? getInstructions().applu(result, values) : result;
	}
	
	@Override
	public Aggregator<T, U> getAggregator(){
		return new AggregatorFunctional<>(this);
	}

}
