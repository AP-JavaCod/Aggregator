package aggregator.container;

public interface Modification<T, U> {

    String getName();

    Container<T, U> getFunction();
}
