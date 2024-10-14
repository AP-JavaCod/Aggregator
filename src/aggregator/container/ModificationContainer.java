package aggregator.container;

import aggregator.AggregatorModification;
import aggregator.lambda.*;
import aggregator.modification.*;

public class ModificationContainer<T, U> implements Modification<T, U> {

    private final String name;
    private final Container<T, U> function;

    public ModificationContainer(ModificationContainer<T, U> modification) {
        this.name = modification.name;
        this.function = modification.function;
    }

    public ModificationContainer(String name, Container<T, U> function) {
        this.name = name;
        this.function = function;
    }

    public ModificationContainer(String name, Calculate<U> calculate, Conversion<T, U> conversion) {
        this.name = name;
        this.function = ContainerFunction.getInstance(calculate, conversion);
    }

    public ModificationContainer(String name, Calculate<U> calculate) {
        this.name = name;
        this.function = (Container<T, U>) ContainerFunction.getInstance(calculate);
    }

    @Override
    public Container<T, U> getFunction() {
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
        return new AggregatorFilterX<>(this) {
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
