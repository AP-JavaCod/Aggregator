package aggregator.lambda;

public interface Conversion<T, U> {

    U convert(T values);

}
