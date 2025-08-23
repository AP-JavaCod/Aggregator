package com.apjc.example;

import com.apjc.aggregator.*;
import com.apjc.aggregator.instructions.BuilderInstructions;

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
		System.out.println(sumInt.aggregation(arrInt));
		System.out.println(textInt.aggregation(arrInt));
		System.out.println(sumStr.aggregation(arrStr));
		System.out.println(textStr.aggregation(arrStr));
	}

}
