package com.apjc.aggregator.instructions;

import com.apjc.aggregator.function.*;

public class BuilderInstructions {

	public static <T, U> InstructionsFunctional<T, U> createInstructions(Calculator<U> cal, Converter<T, U> con){
		return new InstructionsFunctional<>() {

			@Override
			public U calculate(U val1, U val2) {
				return cal.calculate(val1, val2);
			}

			@Override
			public U transform(T values) {
				return con.transform(values);
			}
			
		};
	}
	
	public static <U> InstructionsFunctional<U, U> createInstructions(Calculator<U> cal){
		return createInstructions(cal, t -> t);
	}
	
	public static <T, U> InstructionsFilter<T, U> createInstructionsFilter(Instructions<? super T, U> ins, Filter<T> fil){
		return new InstructionsFilter<>(ins) {

			@Override
			public boolean filter(T values) {
				return fil.filter(values);
			}
			
		};
	}
	
	public static <T, U> InstructionsFilter<T, U> createInstructionsFilter(Calculator<U> cal, Filter<T> fil, Converter<T, U> con){
		return createInstructionsFilter(createInstructions(cal, con), fil);
	}
	
	public static <U> InstructionsFilter<U, U> createInstructionsFilter(Calculator<U> cal, Filter<U> fil){
		return createInstructionsFilter(cal, fil, t -> t);
	}
	
}
