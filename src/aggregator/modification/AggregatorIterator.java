package aggregator.modification;

import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;
import aggregator.lambda.container.ContainerFunction;
import aggregator.lambda.container.Modification;
import aggregator.modification.adapter.AdapterIterator;

import java.util.Iterator;

public class AggregatorIterator<T, U> extends Modification<T, U> implements Iterable<U> {

    private final T[] VALUES;

    @Deprecated
    public AggregatorIterator(String name, CalculationModification<T, U> modification, T[] values) {
        super(name, modification);
        VALUES = values;
    }

    public AggregatorIterator(Modification<T, U> modification, T[] values){
        super(modification);
        VALUES = values;
    }

    public AggregatorIterator(String name, ContainerFunction<T, U> function, T[] values) {
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
            result = getFunction().calculate(result, data[nextId]);
            nextId++;
            return result;
        }

    }

}
