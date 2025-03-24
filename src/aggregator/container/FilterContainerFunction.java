package aggregator.container;

import aggregator.function.Calculation;
import aggregator.function.Filter;

public abstract class FilterContainerFunction <T, U> implements ContainerFunction<T, U>, Filter<T> {

    @Override
    public U apply(U result, T values) {
        return filter(values) ? applyIsFilter(result, values) : result;
    }

    public static <N, M> FilterContainerFunction<N, M> crate(ContainerFunction<N, M> function, Filter<N> fil){
        return new FilterContainerFunction<>() {
            @Override
            protected M applyIsFilter(M result, N values) {
                return function.apply(result, values);
            }

            @Override
            public boolean filter(N values) {
                return fil.filter(values);
            }

        };
    }

    @Override
    public FilterContainerFunction<T, U> getFilterFunction(Filter<T> fil) {
       return getFilterFunction(fil, Boolean::logicalAnd);
    }

    public FilterContainerFunction<T, U> getFilterFunction(Filter<T> fil, Calculation<Boolean> cal){
        Filter<T> newFilter = (T val) -> cal.calculation(this.filter(val), fil.filter(val));
        return crate(this::applyIsFilter, newFilter);
    }

    protected abstract U applyIsFilter(U result, T values);

}
