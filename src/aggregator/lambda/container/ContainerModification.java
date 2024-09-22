package aggregator.lambda.container;

public interface ContainerModification <T, U> {

    String getName();

    ContainerFunction<T, U> getFunction();
}
