package com.apjc.aggregator.derivatives;

import com.apjc.aggregator.Aggregator;
import com.apjc.aggregator.AggregatorFunctional;
import com.apjc.aggregator.ContainerInstructions;
import com.apjc.aggregator.function.Filter;
import com.apjc.aggregator.instructions.Instructions;

public class AggregatorConditionals<T, U> extends AggregatorFunctional<T, U>{
	
	
	public AggregatorConditionals(Instructions<? super T, U> instructions, Filter<T> filter) {
		super(new Noda<>(instructions, filter));
	}
	
	public void add(Instructions<? super T, U> instructions, Filter<T> filter) {
		((Noda<T, U>)getInstructions()).add(instructions, filter);
	}

	private static class Noda<E, N> extends ContainerInstructions<E, N> implements Instructions<E, N>{
		
		private Filter<? super E> filter;
		private Noda<E, N> next;

		public Noda(Instructions<? super E, N> instructions, Filter<? super E> filter) {
			super(instructions);
			this.filter = filter;
		}
		
		@Override
		public N applu(N result, E values) {
			if(filter != null) {
				if(filter.filter(values)) {
					return getInstructions().applu(result, values);
				}else if(next != null) {
					return next.applu(result, values);
				}
				return result;
			}
			return getInstructions().applu(result, values);
		}
		
		public void add(Instructions<? super E, N> instructions, Filter<? super E> filter) {
			if(next == null) {
				next = new Noda<>(instructions, filter);
			}else {
				next.add(instructions, filter);
			}
		}

		@Override
		public Aggregator<E, N> getAggregator() {
			return null;
		}
		
	}
	
}
