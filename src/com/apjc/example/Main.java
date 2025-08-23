package com.apjc.example;

import com.apjc.aggregator.*;
import com.apjc.aggregator.instructions.*;

public class Main {

	public static void main(String[] args) {
		Integer[] arrInt = {7, 5, 4, 9, 2, 18, 7, 4};
		String[] arrStr = {"Test", "Cat", "Java", "C++", "Dog", "Bob"};
		Aggregator<Integer, Integer> sumInt = BuilderInstructions.createInstructions(Integer::sum).getAggregator();
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
		System.out.println(sumInt.aggregation(arrInt));
		System.out.println(textInt.aggregation(arrInt));
		System.out.println(sumStr.aggregation(arrStr));
		System.out.println(textStr.aggregation(arrStr));
		System.out.println(filterTextInt.aggregation(arrInt));
		System.out.println(filterTextStr.aggregation(arrStr));
	}

}
