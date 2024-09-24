package aggregator.lambda.container;

import aggregator.lambda.Calculate;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;

public abstract class ContainerFunction<T, U> implements Container<T, U>, Calculate<U>, Conversion<T, U> {

    @Override
    public U calculate(U result, T values) {
        return result != null ? apply(result, convert(values)) : convert(values);
    }

    public static <M, N> ContainerFunction<M, N> getInstance(Calculate<N> calculate, Conversion<M, N> conversion) {
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

    public static <M> ContainerFunction<M, M> getInstance(Calculate<M> calculate) {
        return getInstance(calculate, obj -> obj);
    }

    public Modification<T, U> getModification(String name){
        return new Modification<>(name, this::calculate);
    }

    public ContainerFunctionFilter<T, U> getFilter(Filter<T> filter) {
        return ContainerFunctionFilter.getInstance(this, filter, this);
    }

}
