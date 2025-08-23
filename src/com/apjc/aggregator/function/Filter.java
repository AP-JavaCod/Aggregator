package com.apjc.aggregator.function;

@FunctionalInterface
public interface Filter <T> {

	boolean filter(T values);
	
}
