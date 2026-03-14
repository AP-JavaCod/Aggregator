package com.apjc.aggregator.function;

@FunctionalInterface
public interface Filter <T> {

	boolean filter(T values);
	
	default Filter<T> not(){
		return (val) -> !this.filter(val);
	}
	
	default Filter<T> and(Filter<T> fil){
		return (val) -> this.filter(val) && fil.filter(val);
	}
	
	default Filter<T> or(Filter<T> fil){
		return (val) -> this.filter(val) || fil.filter(val);
	}
	
	default Filter<T> xor(Filter<T> fil){
		return (val) -> this.filter(val) ^ fil.filter(val);
	}
	
}
