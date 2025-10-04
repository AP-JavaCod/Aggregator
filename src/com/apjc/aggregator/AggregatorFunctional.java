package com.apjc.aggregator;

import com.apjc.aggregator.instructions.Instructions;

public class AggregatorFunctional<T, U> extends ContainerInstructions<T, U> implements Aggregator<T, U> {
	
	public AggregatorFunctional(Instructions<? super T, U> instructions){
		super(instructions);
	}

	@Override
	public U aggregation(T[] values) {
		U result = null;
		for(T el : values) {
			result = getInstructions().applu(result, el);
		}
		return result;
	}

}
