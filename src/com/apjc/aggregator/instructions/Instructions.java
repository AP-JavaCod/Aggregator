package com.apjc.aggregator.instructions;

import com.apjc.aggregator.function.CreaterAggregator;
import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.AggregatorFunctional;

public interface Instructions <T, U>  {
	
	U applu(U result, T values);
	
	default Aggregator<T, U> createAggregator(){
		return new AggregatorFunctional<>(this);
	}
	
	default <N> Aggregator<T, N> createAggregator(CreaterAggregator<T, U, N> agg){
		return agg.create(this);
	}
	
}
