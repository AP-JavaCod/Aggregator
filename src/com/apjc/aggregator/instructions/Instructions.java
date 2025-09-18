package com.apjc.aggregator.instructions;

import com.apjc.aggregator.Aggregator;

public interface Instructions <T, U>  {
	
	U applu(U result, T values);
	
	Aggregator<T, U> getAggregator();
	
}
