package com.apjc.aggregator.instructions;

import com.apjc.aggregator.function.Calculator;

public abstract class InstructionsType <T> implements Instructions<T, T>, Calculator<T>{
	
	@Override
	public T applu(T result, T values) {
		if(values == null) {
			return result;
		}
		return result != null ? calculate(result, values) : values;
	}

}
