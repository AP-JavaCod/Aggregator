package com.apjc.aggregator.function;

@FunctionalInterface
public interface Calculator <U> {
	
	U calculate(U val1, U val2);
	
}
