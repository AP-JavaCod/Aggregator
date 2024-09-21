package aggregator.modification;

import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;
import aggregator.lambda.container.ContainerFunction;
import aggregator.modification.adapter.AdapterIterator;

import java.util.Iterator;

public class AggregatorIterator<T, U> implements Iterable<U> {

    private final String NAME;
    private final ContainerFunction<T, U> MODIFICATION;
    private final T[] VALUES;

    @Deprecated
    public AggregatorIterator(String name, CalculationModification<T, U> modification, T[] values) {
        NAME = name;
        MODIFICATION = ContainerFunction.getInstance(modification);
        VALUES = values;
    }

    public AggregatorIterator(String name, ContainerFunction<T, U> function, T[] values) {
        NAME = name;
        MODIFICATION = function;
        VALUES = values;
    }

    public AggregatorIterator(String name, Calculate<U> calculate, Conversion<T, U> conversion, T[] values) {
        NAME = name;
        MODIFICATION = ContainerFunction.getInstance(calculate, conversion);
        VALUES = values;
    }

    public AggregatorIterator(String name, Calculate<U> calculate, T[] values) {
        NAME = name;
        MODIFICATION = (ContainerFunction<T, U>) ContainerFunction.getInstance(calculate);
        VALUES = values;
    }

    public String getName() {
        return NAME;
    }

    public ContainerFunction<T, U> getModification() {
        return MODIFICATION;
    }

    @Override
    public Iterator<U> iterator() {
        return new ValuesIterator(VALUES);
    }

    public Iterator<U> iterator(T[] values) {
        return new ValuesIterator(values);
    }

    public AdapterIterator<T, U> getAggregator() {
        return new AdapterIterator<>(this);
    }

    private class ValuesIterator implements Iterator<U> {

        private final T[] data;
        private U result = null;
        private int nextId = 0;

        public ValuesIterator(T[] values) {
            this.data = values;
        }

        @Override
        public boolean hasNext() {
            return nextId < data.length;
        }

        @Override
        public U next() {
            result = MODIFICATION.calculate(result, data[nextId]);
            nextId++;
            return result;
        }

    }

}
