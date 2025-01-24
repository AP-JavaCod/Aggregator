package aggregator.container;

import aggregator.function.Filter;

public abstract class FilterContainerFunction <T, U> implements ContainerFunction<T, U>, Filter<T> {

    @Override
    public U apply(U result, T values) {
        if (filter(values)){
            return applyIsFilter(result, values);
        }
        return result;
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

    protected abstract U applyIsFilter(U result, T values);

}
