package aggregator.lambda.container;

import aggregator.AggregatorModification;
import aggregator.lambda.*;
import aggregator.modification.*;

public class Modification<T, U> implements ContainerModification<T, U> {

    private final String name;
    private final ContainerFunction<T, U> function;

    public Modification(Modification<T, U> modification) {
        this.name = modification.name;
        this.function = modification.function;
    }

    public Modification(String name, ContainerFunction<T, U> function) {
        this.name = name;
        this.function = function;
    }

    public Modification(String name, Calculate<U> calculate, Conversion<T, U> conversion) {
        this.name = name;
        this.function = ContainerFunction.getInstance(calculate, conversion);
    }

    public Modification(String name, Calculate<U> calculate) {
        this.name = name;
        this.function = (ContainerFunction<T, U>) ContainerFunction.getInstance(calculate);
    }

    @Override
    public ContainerFunction<T, U> getFunction() {
        return function;
    }

    @Override
    public String getName() {
        return name;
    }

    public U calculate(U result, T values) {
        return function.calculate(result, values);
    }

    public AggregatorModification<T, U> getAggregator() {
        return new AggregatorModification<>(this);
    }

    public <F> AggregatorFilterX<T, U, F> getFilterX(Filter<F> fil) {
        return new AggregatorFilterX<T, U, F>(this) {
            @Override
            public boolean filter(F values) {
                return fil.filter(values);
            }
        };
    }

    public AggregatorIterator<T, U> getIterator(T[] values) {
        return new AggregatorIterator<>(this, values);
    }

}
