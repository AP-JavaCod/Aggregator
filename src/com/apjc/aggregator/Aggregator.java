package com.apjc.aggregator;

public interface Aggregator <T, U> {

	U aggregation(T[] values);
	
}
