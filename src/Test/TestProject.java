package Test;

import aggregator.AggregatorModification;
import aggregator.lambda.CalculationModification;

public class TestProject {

    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        AggregatorModification<Integer, Integer> sum = new AggregatorModification<>("Sum", Integer::sum);
        AggregatorModification<Integer, String> text = new AggregatorModification<>("Text", (s, t) -> s + t, i -> i + ", ");
        System.out.println(sum.aggregatorString(array));
        System.out.println(text.aggregatorString(array));

        CalculationModification<Integer, Integer> sumLambda = CalculationModification.getInstance(Integer::sum);
        CalculationModification<Integer, String> textLambda = CalculationModification.getInstance((s, t) -> s + t, i -> i + ", ");

        AggregatorModification<Integer, Integer> sumCM = new AggregatorModification<>("Sum", sumLambda);
        AggregatorModification<Integer, String> textCM = new AggregatorModification<>("Text", textLambda);
        System.out.println(sumCM.aggregatorString(array));
        System.out.println(textCM.aggregatorString(array));

        AggregatorModification<Integer, Integer> sumGet = sumLambda.getAggregator("Sum");
        AggregatorModification<Integer, String> textGet = textLambda.getAggregator("Text");
        System.out.println(sumCM.aggregatorString(array));
        System.out.println(textCM.aggregatorString(array));
    }

}
