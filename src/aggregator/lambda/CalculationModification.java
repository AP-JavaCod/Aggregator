package aggregator.lambda;

import aggregator.AggregatorModification;
import aggregator.modification.AggregatorFilterX;
import aggregator.modification.AggregatorIterator;

@Deprecated
public abstract class CalculationModification<T, U> implements Calculate<U>, Conversion<T, U> {

    public U applyModification(U val1, T val2) {
        return apply(val1, convert(val2));
    }

    public static <M, N> CalculationModification<M, N> getInstance(Calculate<N> calculate, Conversion<M, N> conversion) {
        return new CalculationModification<>() {
            @Override
            public N apply(N val1, N val2) {
                return calculate.apply(val1, val2);
            }

            @Override
            public N convert(M values) {
                return conversion.convert(values);
            }
        };
    }

    public static <M> CalculationModification<M, M> getInstance(Calculate<M> calculate) {
        return getInstance(calculate, obj -> obj);
    }

    public AggregatorModification<T, U> getAggregator(String name) {
        return new AggregatorModification<>(name, this);
    }

    public CalculationFilter<T, U> getFilter(Filter<T> filter) {
        return CalculationFilter.getInstance(this, filter);
    }

    public <F> AggregatorFilterX<T, U, F> getAggregatorFilterX(String name, Filter<F> fil) {
        return new AggregatorFilterX<>(name, this) {
            @Override
            public boolean filter(F values) {
                return fil.filter(values);
            }
        };
    }

    public AggregatorIterator<T, U> getIterator(String name, T[] values) {
        return new AggregatorIterator<>(name, this, values);
    }

}
