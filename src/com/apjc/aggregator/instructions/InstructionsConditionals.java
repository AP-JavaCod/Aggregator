package com.apjc.aggregator.instructions;

import java.util.List;
import java.util.ArrayList;
import com.apjc.aggregator.ContainerInstructions;

public class InstructionsConditionals<T, U> extends ContainerInstructions<T, U> implements Instructions<T, U> {

	private final List<InstructionsFilter<? super T, U>> MAP = new ArrayList<>();
	
	public InstructionsConditionals(Instructions<? super T, U> defaultInstructions) {
		super(defaultInstructions);
	}
	
	public void add(InstructionsFilter<? super T, U> instructions) {
		MAP.add(instructions);
	}
	
	public void remove(int index) {
		MAP.remove(index);
	}

	@Override
	public U applu(U result, T values) {
		for(InstructionsFilter<? super T, U> ins : MAP) {
			if(values != null && ins.filter(values)) {
				return ins.applu(result, values);
			}
		}
		return getInstructions().applu(result, values);
	}

}
