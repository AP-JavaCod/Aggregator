package aggregator.modification;

import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;

import java.util.Iterator;

public class AggregatorIterator <T, U> implements Iterable<U>{

    private final String NAME;
    private final CalculationModification<T, U> MODIFICATION;
    private final T[] VALUES;

    public AggregatorIterator(String name, CalculationModification<T, U> modification, T[] values){
        NAME = name;
        MODIFICATION = modification;
        VALUES = values;
    }

    public AggregatorIterator(String name, Calculate<U> calculate, Conversion<T, U> conversion, T[] values){
        NAME = name;
        MODIFICATION = CalculationModification.getInstance(calculate, conversion);
        VALUES = values;
    }

    public AggregatorIterator(String name, Calculate<U> calculate, T[] values){
        NAME = name;
        MODIFICATION = (CalculationModification<T, U>) CalculationModification.getInstance(calculate);
        VALUES = values;
    }

    public String getName() {
        return NAME;
    }

    public CalculationModification<T, U> getModification() {
        return MODIFICATION;
    }

    @Override
    public Iterator<U> iterator() {
        return new ValuesIterator();
    }

    private class ValuesIterator implements Iterator<U>{

        private U result = null;
        private int nextId = 0;

        @Override
        public boolean hasNext() {
            return nextId < VALUES.length;
        }

        @Override
        public U next() {
            result = (result != null)? MODIFICATION.applyModification(result, VALUES[nextId]):
                                       MODIFICATION.convert(VALUES[nextId]);
            nextId++;
            return result;
        }

        public String nextString(){
            return NAME + " iteration " + nextId + ": " + next();
        }

    }

}
