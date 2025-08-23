package com.apjc.aggregator;

import com.apjc.aggregator.instructions.Instructions;

public class AggregatorFunctional<T, U> implements Aggregator<T, U> {
	
	private final Instructions<? super T, U> INSTRUCTIONS;
	
	public AggregatorFunctional(Instructions<? super T, U> instructions){
		INSTRUCTIONS = instructions;
	}

	@Override
	public U aggregation(T[] values) {
		U result = null;
		for(T el : values) {
			if(el == null) {
				continue;
			}
			result = INSTRUCTIONS.applu(result, el);
		}
		return result;
	}

}
