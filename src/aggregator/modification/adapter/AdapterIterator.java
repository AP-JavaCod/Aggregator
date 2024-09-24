package aggregator.modification.adapter;

import aggregator.Aggregator;
import aggregator.lambda.Calculate;
import aggregator.lambda.Conversion;
import aggregator.lambda.container.ContainerFunction;
import aggregator.lambda.container.Modification;
import aggregator.modification.AggregatorIterator;

import java.util.Iterator;

public class AdapterIterator<T, U> extends Modification<T, U> implements Aggregator<T, U> {

    private final AggregatorIterator<T, U> AGGREGATOR;

    public AdapterIterator(Modification<T, U> modification, T[] array) {
        super(modification);
        AGGREGATOR = getIterator(array);
    }

    public AdapterIterator(String name, ContainerFunction<T, U> function, T[] array) {
        super(name, function);
        AGGREGATOR = getIterator(array);
    }

    public AdapterIterator(String name, Calculate<U> calculate, Conversion<T, U> conversion, T[] array) {
        super(name, calculate, conversion);
        AGGREGATOR = getIterator(array);
    }

    public AdapterIterator(String name, Calculate<U> calculate, T[] array) {
        super(name, calculate);
        AGGREGATOR = getIterator(array);
    }

    public AdapterIterator(AggregatorIterator<T, U> aggregator) {
        super(aggregator);
        AGGREGATOR = aggregator;
    }

    public U aggregation() {
        U result = null;
        for (U val : AGGREGATOR) {
            result = val;
        }
        return result;
    }

    @Override
    public U aggregation(T[] values) {
        U result = null;
        Iterator<U> iterator = AGGREGATOR.iterator(values);
        while (iterator.hasNext()) {
            result = iterator.next();
        }
        return result;
    }

    @Override
    public String getName() {
        return AGGREGATOR.getName();
    }

    public String aggregationString() {
        return AGGREGATOR.getName() + ": " + aggregation();
    }

}
