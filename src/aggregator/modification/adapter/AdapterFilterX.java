package aggregator.modification.adapter;

import aggregator.Aggregator;
import aggregator.modification.AggregatorFilterX;

public class AdapterFilterX<T, U, F> implements Aggregator<T, U> {

    private final AggregatorFilterX<T, U, F> AGGREGATOR;
    private final F[] FILER_DATA;

    public AdapterFilterX(AggregatorFilterX<T, U, F> aggregator, F[] filterData) {
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
