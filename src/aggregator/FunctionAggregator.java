package aggregator;

import aggregator.container.ContainerFunction;

import java.util.List;

public abstract class FunctionAggregator <T, U> implements Aggregator<T, U> {

    @Override
    public U aggregation(List<T> values) {
        U result = null;
        for (T v : values){
            result = applu(result, v);
        }
        return result;
    }

    public static <N, M> FunctionAggregator<N, M> crate(ContainerFunction<N, M> function){
        return new FunctionAggregator<>() {
            @Override
            protected M applu(M result, N values) {
                return function.apply(result, values);
            }
        };
    }

    protected abstract U applu(U result, T values);

}
