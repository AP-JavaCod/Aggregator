package com.apjc.aggregator.function;

@FunctionalInterface
public interface Converter <T, U> {

	U transform(T values);
	
}
