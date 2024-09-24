package aggregator.lambda.container;

public interface Container <T, U> {

    U calculate(U result, T values);

}
