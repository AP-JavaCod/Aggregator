package test;

import aggregator.Aggregator;
import aggregator.container.ContainerFunction;
import aggregator.container.CalculationContainerFunction;
import aggregator.container.FilterContainerFunction;

public class Main {

    public static void main(String[] args) {
        String[] strings = {"1", "15", "7", "4", "12", "55"};
        ContainerFunction<String, Integer> sumFunction = CalculationContainerFunction.crate(Integer::parseInt, Integer::sum);
        ContainerFunction<String, Integer> sumFilterFunction = FilterContainerFunction.crate(sumFunction, s -> s.length() == 1);
        Aggregator<String, Integer> sumAggregator = sumFunction.getAggregator();
        Aggregator<String, Integer> sumFilterAggregator = sumFilterFunction.getAggregator();
        System.out.println(sumAggregator.aggregation(strings));
        System.out.println(sumFilterAggregator.aggregation(strings));
    }

}
