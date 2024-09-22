package Test;

import aggregator.Aggregator;
import aggregator.AggregatorModification;
import aggregator.lambda.Filter;
import aggregator.lambda.container.ContainerFunction;
import aggregator.lambda.container.Modification;
import aggregator.modification.AggregatorFilterX;
import aggregator.modification.AggregatorIterator;

public class TestProject {

    public static void main(String[] args) {
        System.out.println("Function");
        ContainerFunction<Integer, Integer> sumLambda = ContainerFunction.getInstance(Integer::sum);
        ContainerFunction<Integer, String> textLambda = ContainerFunction.getInstance((s, t) -> s + t, i -> i + ", ");
        print(sumLambda.getModification("Sum"), textLambda.getModification("Text"));
        System.out.println("================================");
        System.out.println("Filter");
        Filter<Integer> filter = i -> i % 2 == 0;
        print(sumLambda.getFilter(filter).getModification("Sum"), textLambda.getFilter(filter).getModification("Text"));
    }

    public static void print(Aggregator<Integer, ?> aggregator){
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(aggregator.aggregationString(array));
    }

    public static void print(Modification<Integer, Integer> sumLambda, Modification<Integer, String> textLambda){
        Integer[] array = {1, 4, 5, 8};
        String[] data = {"Java", "C++", "Java", "C#", "Kotlin", "JS", "Java", "C++", "JS"};
        System.out.println("Aggregator modification");
        AggregatorModification<Integer, Integer> sumModification = sumLambda.getAggregator();
        AggregatorModification<Integer, String> textModification = textLambda.getAggregator();
        print(sumModification);
        print(textModification);
        System.out.println("Aggregator filterX");
        AggregatorFilterX<Integer, Integer, String> sumFilterX = sumLambda.getFilterX(s -> s.equals("Java"));
        AggregatorFilterX<Integer, String, String> textFilterX = textLambda.getFilterX(s -> s.equals("Java"));
        print(sumFilterX.getAggregator(data));
        print(textFilterX.getAggregator(data));
        System.out.println("Aggregator Iterator");
        AggregatorIterator<Integer, Integer> sumIterator = sumLambda.getIterator(array);
        AggregatorIterator<Integer, String> textIterator = textLambda.getIterator(array);
        print(sumIterator.getAggregator());
        print(textIterator.getAggregator());
        System.out.println("For each");
        for (Integer i: sumIterator){
            System.out.println(i);
        }
        for (String t : textIterator){
            System.out.println(t);
        }
    }

}
