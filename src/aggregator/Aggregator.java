package aggregator;

public interface Aggregator <T, U> {

    U aggregation(T[] values);
    String getName();

    default String aggregationString(T[] values){
        return getName() + ": " + aggregation(values);
    }

}
