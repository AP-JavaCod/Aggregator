package aggregator.lambda;

public interface Calculate <U> {

    U apply(U val1, U val2);

}
