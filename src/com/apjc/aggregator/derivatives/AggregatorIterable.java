package com.apjc.aggregator.derivatives;

import java.util.Iterator;
import com.apjc.aggregator.*;
import com.apjc.aggregator.instructions.*;

public class AggregatorIterable <T, U> implements Aggregator<T, Iterable<U>>{
	
	private final Instructions<? super T, U> INSTRUCTIONS;
	
	public AggregatorIterable(Instructions<? super T, U> instructions) {
		INSTRUCTIONS = instructions;
	}

	@Override
	public Iterable<U> aggregation(T[] data){
		return new Iterable<>() {

			@Override
			public Iterator<U> iterator() {
				return new IteratorCalculator(data);
			}
			
		};
	}
	
	public Instructions<? super T, U> getInstructions(){
		return INSTRUCTIONS;
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
			result = INSTRUCTIONS.applu(result, DATA[index]);
			index++;
			return result;
		}
		
		
	}

}
