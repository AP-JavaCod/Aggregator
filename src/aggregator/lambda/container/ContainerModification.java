package aggregator.lambda.container;

public interface ContainerModification <T, U> {

    String getName();

    Container<T, U> getFunction();
}
