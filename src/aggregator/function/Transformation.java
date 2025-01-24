package aggregator.function;

@FunctionalInterface
public interface Transformation <T, U> {

    U transformation(T values);

}
