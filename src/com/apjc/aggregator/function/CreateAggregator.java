package com.apjc.aggregator.function;

import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.instructions.Instructions;

@FunctionalInterface
public interface CreateAggregator<T, U, N> {

 	Aggregator<T, N> create(Instructions<T, U> instructions);
	
}
