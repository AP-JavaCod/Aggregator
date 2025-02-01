package aggregator;

import aggregator.container.CalculationContainerFunction;
import aggregator.container.ContainerFunction;
import aggregator.container.FilterContainerFunction;
import aggregator.function.Calculation;
import aggregator.function.Filter;
import aggregator.function.Transformation;

import java.util.List;

public abstract class FunctionAggregator <T, U> implements Aggregator<T, U> {

    @Override
    public U aggregation(List<T> values) {
        U result = null;
        for (T v : values){
            result = apply(result, v);
        }
        return result;
    }

    public static <N, M> FunctionAggregator<N, M> crate(ContainerFunction<N, M> function){
        return new FunctionAggregator<>() {
            @Override
            protected M apply(M result, N values) {
                return function.apply(result, values);
            }
        };
    }

    public static <N, M> FunctionAggregator<N, M> crate(Transformation<N, M> trans, Calculation<M> cal){
        return CalculationContainerFunction.crate(trans, cal).getAggregator();
    }

    public static <N> FunctionAggregator<N, N> crate(Calculation<N> cal){
        return CalculationContainerFunction.crate(cal).getAggregator();
    }

    public FunctionAggregator<T, U> getFilterAggregator(Filter<T> fil){
        return crate(FilterContainerFunction.crate(this::apply, fil));
    }

    public ContainerFunction<T, U> getFunction(){
        return this::apply;
    }

    protected abstract U apply(U result, T values);

}
