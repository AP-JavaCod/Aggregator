package aggregator.lambda.container;

import aggregator.AggregatorModification;
import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;

public abstract class ContainerFunction <T, U> implements Calculate<U>, Conversion<T, U> {

    public U calculate(U result, T values){
        return result != null ? apply(result, convert(values)) : convert(values);
    }

    public static <M, N> ContainerFunction<M, N> getInstance(Calculate<N> calculate, Conversion<M, N> conversion){
        return new ContainerFunction<>() {
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

    public static <M> ContainerFunction<M, M> getInstance(Calculate<M> calculate){
        return getInstance(calculate, obj -> obj);
    }

    @Deprecated
    public static <M, N> ContainerFunction<M, N> getInstance(CalculationModification<M, N> modification){
       return getInstance(modification, modification);
    }

    public AggregatorModification<T, U> getAggregator(String name){
        return new AggregatorModification<>(name, this);
    }

    public ContainerFunctionFilter<T, U> getFilter(Filter<T> filter){
        return ContainerFunctionFilter.getInstance(this, filter, this);
    }

}
