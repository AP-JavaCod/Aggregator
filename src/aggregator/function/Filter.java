package aggregator.function;

@FunctionalInterface
public interface Filter <T> {

    boolean filter(T values);

}
