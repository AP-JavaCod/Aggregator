package com.apjc.aggregator.instructions;

import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.AggregatorFunctional;

public abstract class InstructionsFunctional<T, U> implements Instructions<T, U> {
	
	@Override
	public U applu(U result, T values) {
		U pas = transform(values);
		return result != null ? calculate(result, pas) : pas;
	}
	
	@Override
	public Aggregator<T, U> getAggregator(){
		return new AggregatorFunctional<>(this);
	}

}
