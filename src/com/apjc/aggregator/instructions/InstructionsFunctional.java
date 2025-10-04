package com.apjc.aggregator.instructions;

import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.AggregatorFunctional;
import com.apjc.aggregator.function.Calculator;
import com.apjc.aggregator.function.Converter;

public abstract class InstructionsFunctional<T, U> implements Instructions<T, U>, Converter<T, U>, Calculator<U> {
	
	@Override
	public U applu(U result, T values) {
		if(values == null) {
			return result;
		}
		U pas = transform(values);
		return result== null ? pas : calculate(result, pas);
	}
	
	@Override
	public Aggregator<T, U> getAggregator(){
		return new AggregatorFunctional<>(this);
	}

}
