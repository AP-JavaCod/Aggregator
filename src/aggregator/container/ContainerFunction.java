package aggregator.container;

import aggregator.FunctionAggregator;

public interface ContainerFunction <T, U> {

    U apply(U result, T values);

    default FunctionAggregator<T, U> getAggregator(){
        return FunctionAggregator.crate(this);
    }

}
