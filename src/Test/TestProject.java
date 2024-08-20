package Test;

import aggregator.AggregatorFilter;
import aggregator.AggregatorModification;
import aggregator.lambda.CalculationModification;
import aggregator.modification.AggregatorFilterX;
import aggregator.modification.AggregatorIterator;

public class TestProject {

    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        String[] data = {"Java", "C++", "Java", "C#", "Kotlin", "JS", "Java", "C++", "JS"};
        CalculationModification<Integer, Integer> sumLambda = CalculationModification.getInstance(Integer::sum);
        CalculationModification<Integer, String> textLambda = CalculationModification.getInstance((s, t) -> s + t, i -> i + ", ");
        System.out.println("Aggregator modification");
        AggregatorModification<Integer, Integer> sumModification = sumLambda.getAggregator("Sum");
        AggregatorModification<Integer, String> textModification = textLambda.getAggregator("Text");
        System.out.println(sumModification.aggregationString(array));
        System.out.println(textModification.aggregationString(array));
        System.out.println("Aggregator filter");
        AggregatorFilter<Integer, Integer> sumFilter = sumLambda.getFilter(i -> i % 2 == 0).getAggregator("Sum");
        AggregatorFilter<Integer, String> textFilter = textLambda.getFilter(i -> i % 2 == 0).getAggregator("Text");
        System.out.println(sumFilter.aggregationString(array));
        System.out.println(textFilter.aggregationString(array));
        System.out.println("Aggregator filterX");
        AggregatorFilterX<Integer, Integer, String> sumFilterX = sumLambda.getAggregatorFilterX("Sum", s -> s.equals("Java"));
        AggregatorFilterX<Integer, String, String> textFilterX = textLambda.getAggregatorFilterX("Text", s -> s.equals("Java"));
        System.out.println(sumFilterX.aggregation(array, data));
        System.out.println(textFilterX.aggregation(array, data));
        System.out.println("Aggregator Iterator");
        AggregatorIterator<Integer, Integer> sumIterator = sumLambda.getIterator("Sum", array);
        AggregatorIterator<Integer, String> textIterator = textLambda.getIterator("Text", array);
        for (int i : sumIterator) {
            System.out.println(i);
        }
        for (String s : textIterator) {
            System.out.println(s);
        }
    }

}
