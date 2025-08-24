package com.apjc.example;

import com.apjc.aggregator.*;
import com.apjc.aggregator.instructions.*;

public class ExampleAggregator {

	public static void main(String[] args) {
		Integer[] arrInt = {7, 5, 4, 9, 2, 18, 3};
		String[] arrStr = {"Test", "Cat", "Java", "C++", "Dog", "Bob"};
		Instructions<Integer, Integer> sum = BuilderInstructions.createInstructions(Integer::sum);
		Instructions<Object, Integer> qI = BuilderInstructions.createInstructions(Integer::sum, _ -> 1);
		Aggregator<Integer, Integer> sumInt = sum.getAggregator();
		Aggregator<Object, String> textInt = BuilderInstructions.createInstructions(
				(v1, v2) -> v1 + "; " + v2, Object::toString).getAggregator();
		Aggregator<String, Integer> sumStr = BuilderInstructions.createInstructions(
				Integer::sum, (String i) -> i.length()).getAggregator();
		Aggregator<String, String> textStr = BuilderInstructions.createInstructions(
				(String v1, String v2) -> v1 + "; " + v2).getAggregator();
		Aggregator<Integer, String> filterTextInt = BuilderInstructions.createInstructionsFilter(
				(String v1, String v2) -> v1 + "; " + v2, (Integer i) -> i % 2 == 0, Object::toString)
				.getAggregator();
		Aggregator<String, String> filterTextStr = BuilderInstructions.createInstructionsFilter(
				(v1, v2) -> v1 + "; " + v2, (String i) -> i.contains("a")).getAggregator();
		
		Aggregator<Integer, Integer> q = new AggregatorX<>(sum, qI) {

			@Override
			protected Integer getResult(Integer val1, Integer val2) {
				return val1 / val2;
			}
			
		};
		
		Aggregator<Integer, Integer> qF = new AggregatorX<>(sum, qI, i -> i % 2 == 0) {

			@Override
			protected Integer getResult(Integer val1, Integer val2) {
				return val1 / val2;
			}
			
		};
		
		System.out.println(sumInt.aggregation(arrInt));
		System.out.println(textInt.aggregation(arrInt));
		System.out.println(sumStr.aggregation(arrStr));
		System.out.println(textStr.aggregation(arrStr));
		System.out.println(filterTextInt.aggregation(arrInt));
		System.out.println(filterTextStr.aggregation(arrStr));
		System.out.println(q.aggregation(arrInt));
		System.out.println(qF.aggregation(arrInt));
	}

}
