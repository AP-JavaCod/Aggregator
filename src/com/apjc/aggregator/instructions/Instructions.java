package com.apjc.aggregator.instructions;

import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.function.Calculator;
import com.apjc.aggregator.function.Converter;

public interface Instructions <T, U> extends Calculator<U>, Converter<T, U> {
	
	U applu(U result, T values);
	
	Aggregator<T, U> getAggregator();
	
}
