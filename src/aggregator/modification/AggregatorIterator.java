package aggregator.modification;

import aggregator.lambda.Calculate;
import aggregator.lambda.Conversion;
import aggregator.lambda.container.Container;
import aggregator.lambda.container.Modification;
import aggregator.modification.adapter.AdapterIterator;

import java.util.Iterator;

public class AggregatorIterator<T, U> extends Modification<T, U> implements Iterable<U> {

    private final T[] VALUES;

    public AggregatorIterator(Modification<T, U> modification, T[] values) {
        super(modification);
        VALUES = values;
    }

    public AggregatorIterator(String name, Container<T, U> function, T[] values) {
        super(name, function);
        VALUES = values;
    }

    public AggregatorIterator(String name, Calculate<U> calculate, Conversion<T, U> conversion, T[] values) {
        super(name, calculate, conversion);
        VALUES = values;
    }

    public AggregatorIterator(String name, Calculate<U> calculate, T[] values) {
        super(name, calculate);
        VALUES = values;
    }

    public Object[] aggregatorAll() {
        Object[] array = new Object[VALUES.length];
        int i = 0;
        for (U val : this) {
            array[i] = val;
            i++;
        }
        return array;
    }

    @Override
    public Iterator<U> iterator() {
        return new ValuesIterator(VALUES);
    }

    public Iterator<U> iterator(T[] values) {
        return new ValuesIterator(values);
    }

    public AdapterIterator<T, U> getAdapter() {
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
            result = calculate(result, data[nextId]);
            nextId++;
            return result;
        }

    }

}
