package com.apjc.example;

import com.apjc.aggregator.*;
import com.apjc.aggregator.instructions.*;

public class ExampleAggregator {

	public static void main(String[] args) {
		Integer[] arrInt = {7, 5, 4, 9, 2, 18, 3};
		String[] arrStr = {"Test", "Cat", "Java", "C++", "Dog", "Bob"};
		Instructions<Integer, Integer> sum = BuilderInstructions.createInstructions(Integer::sum);
		Instructions<Object, Integer> qI = BuilderInstructions.createInstructions(Integer::sum, _ -> 1);
		
		System.out.println("===AggregatorFunctional===");
		Aggregator<Integer, Integer> sumInt = sum.getAggregator();
		Aggregator<Object, String> textInt = BuilderInstructions.createInstructions(
				(v1, v2) -> v1 + "; " + v2, Object::toString).getAggregator();
		Aggregator<String, Integer> sumStr = BuilderInstructions.createInstructions(
				Integer::sum, String::length).getAggregator();
		Aggregator<String, String> textStr = BuilderInstructions.createInstructions(
				(String v1, String v2) -> v1 + "; " + v2).getAggregator();
		System.out.println(sumInt.aggregation(arrInt));
		System.out.println(textInt.aggregation(arrInt));
		System.out.println(sumStr.aggregation(arrStr));
		System.out.println(textStr.aggregation(arrStr));
		
		System.out.println("\n===InstructionsFilter===");
		Aggregator<Integer, String> filterTextInt = BuilderInstructions.createInstructionsFilter(
				(String v1, String v2) -> v1 + "; " + v2, (Integer i) -> i % 2 == 0, Object::toString)
				.getAggregator();
		Aggregator<String, String> filterTextStr = BuilderInstructions.createInstructionsFilter(
				(v1, v2) -> v1 + "; " + v2, (String i) -> i.contains("a")).getAggregator();
		System.out.println(filterTextInt.aggregation(arrInt));
		System.out.println(filterTextStr.aggregation(arrStr));
		
		System.out.println("\n===InstructionsX===");
		InstructionsX<Integer, Integer> q = new InstructionsX<>(sum, qI);
		Instructions<Integer,InstructionsX.Result<Integer>> qF = BuilderInstructions.createInstructionsFilter(q, i -> i % 2 == 0);
		System.out.println(q.getAggregator().aggregation(arrInt).applu((a, b) -> a / b));
		System.out.println(qF.getAggregator().aggregation(arrInt).applu((a, b) -> a / b));
		
		System.out.println("\n===AggregatorIterable===");
		AggregatorIterable<Integer, Integer> iterable = new AggregatorIterable<>(sum);
		for(int s : iterable.aggregation(arrInt)){
			System.out.println(s);
		}
		
		System.out.println("\n===InstructionsConditionals===");
		Instructions<Integer, String> insB = BuilderInstructions.createInstructions((a, b) -> a + " " + b, 
				i -> i.toString() + "%3V");
		Instructions<Integer, String> def = BuilderInstructions.createInstructions((a, b) -> a + " " + b, 
				i -> i.toString() + "%NV");
		InstructionsConditionals<Integer, String> conditionals = new InstructionsConditionals<>(BuilderInstructions
				.createInstructionsFilter((a, b) -> a + " " + b, i -> i % 2 == 0, i -> i.toString() + "%2V"));
		conditionals.add(insB, i -> i % 3 == 0);
		conditionals.add(def, null);
		System.out.println(conditionals.getAggregator().aggregation(arrInt));
	}

}
