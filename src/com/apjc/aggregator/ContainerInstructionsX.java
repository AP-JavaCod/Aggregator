package com.apjc.aggregator;

import com.apjc.aggregator.instructions.InstructionsX;
import com.apjc.aggregator.function.Calculator;

public class ContainerInstructionsX<T, U> extends ContainerInstructions<T, InstructionsX.Result<U>> {

	private final Calculator<U> CALCULATOR;
	
	public ContainerInstructionsX(InstructionsX<T, U> instructions, Calculator<U> calculator) {
		super(instructions);
		CALCULATOR = calculator;
	}
	
	public Calculator<U> getCalculator(){
		return CALCULATOR;
	}
	
	public U calculate(InstructionsX.Result<U> resurs) {
		return resurs.applu(CALCULATOR);
	}
	
}
