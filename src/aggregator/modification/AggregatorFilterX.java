package aggregator.modification;

import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;
import aggregator.lambda.container.ContainerFunction;
import aggregator.modification.adapter.AdapterFilterX;

public abstract class AggregatorFilterX<T, U, F> implements Filter<F> {

    private final String NAME;
    private final ContainerFunction<T, U> MODIFICATION;

    @Deprecated
    public AggregatorFilterX(String name, CalculationModification<T, U> modification) {
        NAME = name;
        MODIFICATION = ContainerFunction.getInstance(modification);
    }

    public AggregatorFilterX(String name, ContainerFunction<T, U> function){
        NAME = name;
        MODIFICATION = function;
    }

    public AggregatorFilterX(String name, Calculate<U> calculate, Conversion<T, U> conversion) {
        NAME = name;
        MODIFICATION = ContainerFunction.getInstance(calculate, conversion);
    }

    public AggregatorFilterX(String name, Calculate<U> calculate) {
        NAME = name;
        MODIFICATION = (ContainerFunction<T, U>) ContainerFunction.getInstance(calculate);
    }

    public U aggregation(T[] values, F[] fil) {
        if (values.length <= fil.length) {
            U result = null;
            for (int i = 0; i < values.length; i++) {
                if (filter(fil[i])) {
                    result = MODIFICATION.calculate(result, values[i]);
                }
            }
            return result;
        } else {
            throw new ArrayIndexOutOfBoundsException(values.length);
        }
    }

    public String getName() {
        return NAME;
    }

    public ContainerFunction<T, U> getModification() {
        return MODIFICATION;
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

    public AdapterFilterX<T, U, F> getAggregator(F[] dataFilter){
        return new AdapterFilterX<>(this, dataFilter);
    }

}
