package aggregator.lambda;

public interface Filter <T>{

    boolean filter(T values);
}
