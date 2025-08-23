package com.apjc.aggregator.instructions;

import com.apjc.aggregator.function.Calculator;
import com.apjc.aggregator.function.Converter;

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
	
}
