package aggregator.modification;

import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;

public abstract class AggregatorFilterX<T, U, F> implements Filter<F> {

    private final String NAME;
    private final CalculationModification<T, U> MODIFICATION;

    public AggregatorFilterX(String name, CalculationModification<T, U> modification) {
        NAME = name;
        MODIFICATION = modification;
    }

    public AggregatorFilterX(String name, Calculate<U> calculate, Conversion<T, U> conversion) {
        NAME = name;
        MODIFICATION = CalculationModification.getInstance(calculate, conversion);
    }

    public AggregatorFilterX(String name, Calculate<U> calculate) {
        NAME = name;
        MODIFICATION = (CalculationModification<T, U>) CalculationModification.getInstance(calculate);
    }

    public U aggregation(T[] values, F[] fil) {
        if (values.length <= fil.length) {
            U result = null;
            for (int i = 0; i < values.length; i++) {
                if (filter(fil[i])) {
                    if (result != null) {
                        result = MODIFICATION.applyModification(result, values[i]);
                    } else {
                        result = MODIFICATION.convert(values[i]);
                    }
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

    public CalculationModification<T, U> getModification() {
        return MODIFICATION;
    }

    public static <M, N, G> AggregatorFilterX<M, N, G> getInstance(String name, CalculationModification<M, N> modification, Filter<G> fil) {
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

}
