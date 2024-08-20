package aggregator;

import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationFilter;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;

public class AggregatorFilter <T, U> implements Aggregator<T, U>{

    private final String NAME;
    private final CalculationFilter<T, U> FILTER;

    public AggregatorFilter(String name, CalculationFilter<T, U> filter){
        NAME = name;
        FILTER = filter;
    }

    public AggregatorFilter(String name, Calculate<U> calculate, Filter<T> filter, Conversion<T, U> conversion){
        NAME = name;
        FILTER = CalculationFilter.getInstance(calculate, filter, conversion);
    }

    public AggregatorFilter(String name, Calculate<U> calculate, Filter<T> filter){
        NAME = name;
        FILTER = (CalculationFilter<T, U>) CalculationFilter.getInstance((Calculate<T>) calculate, filter);
    }

    @Override
    public U aggregation(T[] values) {
        U result = null;
        for (T i: values){
            if (result != null){
                result = FILTER.applyFilter(result, i);
            }else {
                result = FILTER.convertFilter(i);
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public CalculationFilter<T, U> getFilter() {
        return FILTER;
    }

}
