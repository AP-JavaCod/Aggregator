package aggregator;

import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;
import aggregator.lambda.container.ContainerFunction;

public class AggregatorModification<T, U> implements Aggregator<T, U> {

    private final String NAME;
    private final ContainerFunction<T, U> MODIFICATION;

    @Deprecated
    public AggregatorModification(String name, CalculationModification<T, U> modification) {
        NAME = name;
        MODIFICATION = ContainerFunction.getInstance(modification);
    }

    public AggregatorModification(String name, ContainerFunction<T, U> function){
        NAME = name;
        MODIFICATION = function;
    }

    public AggregatorModification(String name, Calculate<U> calculate, Conversion<T, U> conversion) {
        NAME = name;
        MODIFICATION = ContainerFunction.getInstance(calculate, conversion);
    }

    public AggregatorModification(String name, Calculate<U> calculate) {
        NAME = name;
        MODIFICATION = (ContainerFunction<T, U>) ContainerFunction.getInstance(calculate);
    }

    @Override
    public U aggregation(T[] values) {
        U result = null;
        for (T obj : values){
            result = MODIFICATION.calculate(result, obj);
        }
        return result;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public ContainerFunction<T, U> getModification() {
        return MODIFICATION;
    }

}
