package aggregator.lambda;

import aggregator.AggregatorFilter;

public abstract class CalculationFilter<T, U> implements Calculate<U>, Conversion<T, U>, Filter<T> {

    public U applyFilter(U val1, T val2) {
        return filter(val2) ? apply(val1, convert(val2)) : val1;
    }

    public U convertFilter(T values) {
        return filter(values) ? convert(values) : null;
    }

    public static <M, N> CalculationFilter<M, N> getInstance(Calculate<N> calculate, Filter<M> fil, Conversion<M, N> conversion) {
        return new CalculationFilter<>() {
            @Override
            public N apply(N val1, N val2) {
                return calculate.apply(val1, val2);
            }

            @Override
            public N convert(M values) {
                return conversion.convert(values);
            }

            @Override
            public boolean filter(M values) {
                return fil.filter(values);
            }
        };
    }

    public static <M, N> CalculationFilter<M, N> getInstance(CalculationModification<M, N> modification, Filter<M> fil) {
        return getInstance(modification, fil, modification);
    }

    public static <M> CalculationFilter<M, M> getInstance(Calculate<M> calculate, Filter<M> fil) {
        return getInstance(calculate, fil, obj -> obj);
    }

    public AggregatorFilter<T, U> getAggregator(String name) {
        return new AggregatorFilter<>(name, this);
    }

}
