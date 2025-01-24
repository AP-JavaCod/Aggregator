package aggregator.function;

@FunctionalInterface
public interface Calculation <U> {

    U calculation(U val1, U val2);

}
