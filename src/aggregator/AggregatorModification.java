package aggregator;

import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;

public class AggregatorModification<T, U> implements Aggregator<T, U> {

    private final String NAME;
    private final CalculationModification<T, U> MODIFICATION;

    public AggregatorModification(String name, CalculationModification<T, U> modification) {
        NAME = name;
        MODIFICATION = modification;
    }

    public AggregatorModification(String name, Calculate<U> calculate, Conversion<T, U> conversion) {
        NAME = name;
        MODIFICATION = CalculationModification.getInstance(calculate, conversion);
    }

    public AggregatorModification(String name, Calculate<U> calculate) {
        NAME = name;
        MODIFICATION = (CalculationModification<T, U>) CalculationModification.getInstance(calculate);
    }

    @Override
    public U aggregation(T[] values) {
        U result = MODIFICATION.convert(values[0]);
        for (int i = 1; i < values.length; i++) {
            result = MODIFICATION.applyModification(result, values[i]);
        }
        return result;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public CalculationModification<T, U> getModification() {
        return MODIFICATION;
    }

}
