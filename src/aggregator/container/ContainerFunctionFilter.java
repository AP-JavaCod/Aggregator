package aggregator.container;

import aggregator.lambda.Calculate;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;

public abstract class ContainerFunctionFilter<T, U> extends ContainerFunction<T, U> implements Filter<T> {

    @Override
    public U calculate(U result, T values) {
        return filter(values) ? super.calculate(result, values) : result;
    }

    @Override
    public ContainerFunctionFilter<T, U> getFilter(Filter<T> fil) {
        return super.getFilter(v -> filter(v) && fil.filter(v));
    }

    public static <M, N> ContainerFunctionFilter<M, N> getInstance(Calculate<N> calculate, Filter<M> fil, Conversion<M, N> conversion) {
        return new ContainerFunctionFilter<>() {
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

    public static <M> ContainerFunctionFilter<M, M> getInstance(Calculate<M> calculate, Filter<M> filter) {
        return getInstance(calculate, filter, obj -> obj);
    }

}
