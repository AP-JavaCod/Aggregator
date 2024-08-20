package aggregator.lambda;

import aggregator.AggregatorModification;

public abstract class CalculationModification <T, U> implements Calculate<U>, Conversion<T, U> {

    public U applyModification(U val1, T val2){
        return apply(val1, convert(val2));
    }

    public static <M, N> CalculationModification<M, N> getInstance(Calculate<N> calculate, Conversion<M, N> conversion){
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

    public static <M> CalculationModification<M, M> getInstance(Calculate<M> calculate){
        return getInstance(calculate, obj -> obj);
    }

    public AggregatorModification<T, U> getAggregator(String name){
        return new AggregatorModification<>(name, this);
    }

}
