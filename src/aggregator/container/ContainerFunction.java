package aggregator.container;

import aggregator.FunctionAggregator;
import aggregator.function.Filter;

public interface ContainerFunction <T, U> {

    U apply(U result, T values);

    default FunctionAggregator<T, U> getAggregator(){
        return FunctionAggregator.crate(this);
    }

    default FilterContainerFunction<T, U> getFilterFunction(Filter<T> fil){
        return FilterContainerFunction.crate(this, fil);
    }

}
