package aggregator.lambda.container;

import aggregator.AggregatorModification;
import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;
import aggregator.modification.AggregatorFilterX;
import aggregator.modification.AggregatorIterator;

public abstract class ContainerFunction<T, U> implements Calculate<U>, Conversion<T, U> {

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

    @Deprecated
    public static <M, N> ContainerFunction<M, N> getInstance(CalculationModification<M, N> modification) {
        return getInstance(modification, modification);
    }

    public Modification<T, U> getModification(String name){
        return new Modification<>(name, this);
    }

    /*public AggregatorModification<T, U> getAggregator(String name) {
        return getModification(name).getAggregator();
    }

    public AggregatorIterator<T, U> getIterator(String name, T[] values) {
        return getModification(name).getIterator(values);
    }

    public <F> AggregatorFilterX<T, U, F> getAggregatorFilterX(String name, Filter<F> fil){
        return getModification(name).getFilterX(fil);
    }*/

    public ContainerFunctionFilter<T, U> getFilter(Filter<T> filter) {
        return ContainerFunctionFilter.getInstance(this, filter, this);
    }

}
