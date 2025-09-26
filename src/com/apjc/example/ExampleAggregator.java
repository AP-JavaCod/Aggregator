package com.apjc.example;

import com.apjc.aggregator.*;
import com.apjc.aggregator.instructions.*;
import com.apjc.aggregator.derivatives.AggregatorConditionals;
import com.apjc.aggregator.derivatives.AggregatorIterable;

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
				Integer::sum, (String i) -> i.length()).getAggregator();
		Aggregator<String, String> textStr = BuilderInstructions.createInstructions(
				(String v1, String v2) -> v1 + "; " + v2).getAggregator();
		System.out.println(sumInt.aggregation(arrInt));
		System.out.println(textInt.aggregation(arrInt));
		System.out.println(sumStr.aggregation(arrStr));
		System.out.println(textStr.aggregation(arrStr));
		
		System.out.println("\n===AggregatorFilter===");
		Aggregator<Integer, String> filterTextInt = BuilderInstructions.createInstructionsFilter(
				(String v1, String v2) -> v1 + "; " + v2, (Integer i) -> i % 2 == 0, Object::toString)
				.getAggregator();
		Aggregator<String, String> filterTextStr = BuilderInstructions.createInstructionsFilter(
				(v1, v2) -> v1 + "; " + v2, (String i) -> i.contains("a")).getAggregator();
		System.out.println(filterTextInt.aggregation(arrInt));
		System.out.println(filterTextStr.aggregation(arrStr));
		
		System.out.println("\n===AggregatorX===");
		AggregatorX<Integer, Integer> q = new AggregatorX<>(sum, qI);
		AggregatorX<Integer, Integer> qF = new AggregatorX<>(sum, qI, i -> i % 2 == 0);
		System.out.println(q.aggregation(arrInt, (a, b) -> a / b));
		System.out.println(qF.aggregation(arrInt).applu((a, b) -> a / b));
		
		System.out.println("\n===AggregatorIterable===");
		AggregatorIterable<Integer, Integer> iterable = new AggregatorIterable<>(sum);
		for(int s : iterable.aggregation(arrInt)){
			System.out.println(s);
		}
		
		System.out.println("\n===AggregatorConditionals===");
		Instructions<Integer, String> instructionsA = BuilderInstructions.createInstructions((a, b) -> a + b, i -> i.toString() + "%2V ");
		Instructions<Integer, String> instructionsB = BuilderInstructions.createInstructions((a, b) -> a + b, i -> i.toString() + "%3V ");
		Instructions<Integer, String> instructionsC = BuilderInstructions.createInstructions((a, b) -> a + b, i -> i.toString() + "%NV ");
		AggregatorConditionals<Integer, String> conditionals = new AggregatorConditionals<>(instructionsA, i -> i % 2 == 0);
		conditionals.add(instructionsB, i -> i % 3 == 0);
		conditionals.add(instructionsC, null);
		System.out.println(conditionals.aggregation(arrInt));
	}

}
