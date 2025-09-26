package com.apjc.aggregator.instructions;

import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.AggregatorFunctional;
import com.apjc.aggregator.ContainerInstructions;
import com.apjc.aggregator.function.Filter;

public class InstructionsConditionals<T, U> extends ContainerInstructions<T, U> implements Instructions<T, U> {

	private final Filter<T> FILTER;
	private InstructionsConditionals<T, U> next;
	
	public InstructionsConditionals(Instructions<? super T, U> instructions, Filter<T> filter) {
		super(instructions);
		FILTER = filter;
	}
	
	public InstructionsConditionals(InstructionsFilter<? super T, U> instructions) {
		super(instructions.getInstructions());
		FILTER = instructions::filter;
	}
	
	public void add(Instructions<? super T, U> instructions, Filter<T> filter) {
		if(next == null) {
			next = new InstructionsConditionals<>(instructions, filter);
		}else {
			next.add(instructions, filter);
		}
	}
	
	public void add(InstructionsFilter<? super T, U> instructions) {
		add(instructions.getInstructions(), instructions::filter);
	}

	@Override
	public U applu(U result, T values) {
		if(FILTER == null || FILTER.filter(values)) {
			return getInstructions().applu(result, values);
		}else if(next != null) {
			return next.applu(result, values);
		}
		return result;
	}

	@Override
	public Aggregator<T, U> getAggregator() {
		return new AggregatorFunctional<>(this);
	}

}
