package aggregator.modification.adapter;

import aggregator.Aggregator;
import aggregator.lambda.Calculate;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;
import aggregator.lambda.container.Container;
import aggregator.lambda.container.Modification;
import aggregator.modification.AggregatorFilterX;

public class AdapterFilterX<T, U, F> extends Modification<T, U> implements Aggregator<T, U> {

    private final AggregatorFilterX<T, U, F> AGGREGATOR;
    private final F[] FILER_DATA;

    public AdapterFilterX(Modification<T, U> modification, Filter<F> filter, F[] filterData) {
        super(modification);
        AGGREGATOR = getFilterX(filter);
        FILER_DATA = filterData;
    }

    public AdapterFilterX(String name, Container<T, U> function, Filter<F> filter, F[] filterData) {
        super(name, function);
        AGGREGATOR = getFilterX(filter);
        FILER_DATA = filterData;
    }

    public AdapterFilterX(String name, Calculate<U> calculate, Conversion<T, U> conversion, Filter<F> filter, F[] filterData) {
        super(name, calculate, conversion);
        AGGREGATOR = getFilterX(filter);
        FILER_DATA = filterData;
    }

    public AdapterFilterX(String name, Calculate<U> calculate, Filter<F> filter, F[] filterData) {
        super(name, calculate);
        AGGREGATOR = getFilterX(filter);
        FILER_DATA = filterData;
    }

    public AdapterFilterX(AggregatorFilterX<T, U, F> aggregator, F[] filterData) {
        super(aggregator);
        AGGREGATOR = aggregator;
        FILER_DATA = filterData;
    }

    @Override
    public U aggregation(T[] values) {
        return AGGREGATOR.aggregation(values, FILER_DATA);
    }

    @Override
    public String getName() {
        return AGGREGATOR.getName();
    }
}
