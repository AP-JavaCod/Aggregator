package com.apjc.aggregator;

import java.util.Iterator;
import com.apjc.aggregator.instructions.*;

public class AggregatorIterable <T, U> extends ContainerInstructions<T, U> implements Aggregator<T, Iterable<U>>{
	
	public AggregatorIterable(Instructions<? super T, U> instructions) {
		super(instructions);
	}

	@Override
	public Iterable<U> aggregation(T[] data){
		return () -> new IteratorCalculator(data);
	}
	
	private class IteratorCalculator implements Iterator<U>{
		
		private final T[] DATA;
		private U result = null;
		private int index = 0;
		
		public IteratorCalculator(T[] data) {
			DATA = data;
		}

		@Override
		public boolean hasNext() {
			return index < DATA.length;
		}

		@Override
		public U next() {
			result = getInstructions().applu(result, DATA[index]);
			index++;
			return result;
		}
		
	}

}
