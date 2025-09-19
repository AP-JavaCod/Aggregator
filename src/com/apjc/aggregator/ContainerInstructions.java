package com.apjc.aggregator;

import com.apjc.aggregator.instructions.Instructions;

public class ContainerInstructions <T, U> {
	
	private final Instructions<? super T, U> INSTRUCTIONS;
	
	public ContainerInstructions(Instructions<? super T, U> instructions){
		INSTRUCTIONS = instructions;
	}
	
	public Instructions<? super T, U> getInstructions(){
		return INSTRUCTIONS;
	}

}
