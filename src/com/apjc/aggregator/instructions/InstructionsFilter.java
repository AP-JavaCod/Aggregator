package com.apjc.aggregator.instructions;

import com.apjc.aggregator.function.Filter;

public abstract class InstructionsFilter <T, U> extends InstructionsFunctional<T, U> implements Filter<T> {
	
	@Override
	public U applu(U result, T values) {
		return filter(values) ? super.applu(result, values) : result;
	}

}
