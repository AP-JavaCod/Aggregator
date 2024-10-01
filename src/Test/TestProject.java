package Test;

import aggregator.Aggregator;
import aggregator.AggregatorModification;
import aggregator.lambda.Filter;
import aggregator.container.ContainerFunction;
import aggregator.container.ModificationContainer;
import aggregator.modification.AggregatorFilterX;
import aggregator.modification.AggregatorIterator;
import aggregator.monad.ContainerMonad;

import java.util.Arrays;

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
        Integer[] a = {111, 22, 333333};
        Aggregator<Integer, String> monad = ContainerMonad.from((Integer i) -> String.valueOf(i))
                .map(String::toCharArray)
                .mapAggregation("Test", (String[] result, char[] values) -> {
                    String[] strings = result == null ? new String[0] : result;
                    int l = Math.max(strings.length, values.length);
                    String[] res = new String[l];
                    for (int i = 0; i < l; i++){
                        if (strings.length <= i){
                            res[i] = String.valueOf(values[i]);
                        }else if(values.length <= i){
                            res[i] = strings[i];
                        }else {
                            res[i] = strings[i] + values[i];
                        }
                    }
                    return res;
                }, AggregatorModification::new)
                .finish("ABab", (result, values) -> (result == null ? "" : result + ", ") + values, AggregatorModification::new);
        System.out.println(monad.aggregationString(a));
    }

    public static void print(Aggregator<Integer, ?> aggregator) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(aggregator.aggregationString(array));
    }

    public static void print(ModificationContainer<Integer, Integer> sumLambda, ModificationContainer<Integer, String> textLambda) {
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
        for (Integer i : sumIterator) {
            System.out.println(i);
        }
        System.out.println(Arrays.toString(sumIterator.aggregatorAll()));
        for (String t : textIterator) {
            System.out.println(t);
        }
        System.out.println(Arrays.toString(sumIterator.aggregatorAll()));
    }

}
