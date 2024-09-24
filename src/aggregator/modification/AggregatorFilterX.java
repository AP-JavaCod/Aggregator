package aggregator.modification;

import aggregator.lambda.Calculate;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;
import aggregator.lambda.container.ContainerFunction;
import aggregator.lambda.container.Modification;
import aggregator.modification.adapter.AdapterFilterX;

public abstract class AggregatorFilterX<T, U, F> extends Modification<T, U> implements Filter<F> {

    public AggregatorFilterX(Modification<T, U> modification) {
        super(modification);
    }

    public AggregatorFilterX(String name, ContainerFunction<T, U> function) {
        super(name, function);
    }

    public AggregatorFilterX(String name, Calculate<U> calculate, Conversion<T, U> conversion) {
        super(name, calculate, conversion);
    }

    public AggregatorFilterX(String name, Calculate<U> calculate) {
        super(name, calculate);
    }

    public U aggregation(T[] values, F[] fil) {
        if (values.length <= fil.length) {
            U result = null;
            for (int i = 0; i < values.length; i++) {
                if (filter(fil[i])) {
                    result = calculate(result, values[i]);
                }
            }
            return result;
        } else {
            throw new ArrayIndexOutOfBoundsException(values.length);
        }
    }

    public static <M, N, G> AggregatorFilterX<M, N, G> getInstance(String name, ContainerFunction<M, N> modification, Filter<G> fil) {
        return new AggregatorFilterX<>(name, modification) {
            @Override
            public boolean filter(G values) {
                return fil.filter(values);
            }
        };
    }

    public static <M, N, G> AggregatorFilterX<M, N, G> getInstance(String name, Calculate<N> calculate, Conversion<M, N> conversion, Filter<G> fil) {
        return new AggregatorFilterX<>(name, calculate, conversion) {
            @Override
            public boolean filter(G values) {
                return fil.filter(values);
            }
        };
    }

    public static <M, G> AggregatorFilterX<M, M, G> getInstance(String name, Calculate<M> calculate, Filter<G> fil) {
        return new AggregatorFilterX<>(name, calculate) {
            @Override
            public boolean filter(G values) {
                return fil.filter(values);
            }
        };
    }

    public AdapterFilterX<T, U, F> getAggregator(F[] dataFilter) {
        return new AdapterFilterX<>(this, dataFilter);
    }

}
