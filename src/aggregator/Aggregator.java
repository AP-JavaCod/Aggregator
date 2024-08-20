package aggregator;

public interface Aggregator <T, U> {

    U aggregation(T[] values);
    String getName();

    default String aggregatorString(T[] values){
        return getName() + ": " + aggregation(values);
    }

}
