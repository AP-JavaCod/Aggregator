package aggregator;

import aggregator.lambda.Calculate;
import aggregator.lambda.Conversion;
import aggregator.lambda.container.*;

public class AggregatorModification<T, U> extends Modification<T, U> implements Aggregator<T, U> {

    public AggregatorModification(Modification<T, U> modification) {
        super(modification);
    }

    public AggregatorModification(String name, Container<T, U> function) {
        super(name, function);
    }

    public AggregatorModification(String name, Calculate<U> calculate, Conversion<T, U> conversion) {
        super(name, calculate, conversion);
    }

    public AggregatorModification(String name, Calculate<U> calculate) {
        super(name, calculate);
    }

    @Override
    public U aggregation(T[] values) {
        U result = null;
        for (T obj : values) {
            result = calculate(result, obj);
        }
        return result;
    }

}
