package aggregator.lambda.container;

import aggregator.AggregatorModification;
import aggregator.lambda.Calculate;
import aggregator.lambda.CalculationModification;
import aggregator.lambda.Conversion;
import aggregator.lambda.Filter;
import aggregator.modification.AggregatorFilterX;

public class Modification <T, U> {

    private final String name;
    private final ContainerFunction<T, U> function;

    @Deprecated
    public Modification(String name, CalculationModification<T, U> modification){
        this.name = name;
        this.function = ContainerFunction.getInstance(modification);
    }

    public Modification(Modification<T, U> modification){
        this.name = modification.name;
        this.function = modification.function;
    }

    public Modification(String name, ContainerFunction<T, U> function){
        this.name = name;
        this.function = function;
    }

    public Modification(String name, Calculate<U> calculate, Conversion<T, U> conversion){
        this.name = name;
        this.function = ContainerFunction.getInstance(calculate, conversion);
    }

    public Modification(String name, Calculate<U> calculate){
        this.name = name;
        this.function = (ContainerFunction<T, U>) ContainerFunction.getInstance(calculate);
    }

    public ContainerFunction<T, U> getFunction() {
        return function;
    }

    public String getName() {
        return name;
    }

    public AggregatorModification<T, U> getAggregator(){
        return new AggregatorModification<>(this);
    }

    public <F> AggregatorFilterX<T, U, F> getFilterX(Filter<F> fil){
        return new AggregatorFilterX<T, U, F>(this) {
            @Override
            public boolean filter(F values) {
                return fil.filter(values);
            }
        };
    }

}
