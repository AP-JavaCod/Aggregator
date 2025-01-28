package aggregator.container;

import aggregator.function.Calculation;
import aggregator.function.Transformation;

public abstract class CalculationContainerFunction <T, U> implements ContainerFunction<T, U>, Transformation<T, U>, Calculation<U> {

    @Override
    public final U apply(U result, T values) {
        U trans = transformation(values);
        return result == null ? trans : calculation(result, trans);
    }

    public static <N, M> CalculationContainerFunction<N, M> crate(Transformation<N, M> trans, Calculation<M> cal){
        return new CalculationContainerFunction<>() {
            @Override
            public M calculation(M val1, M val2) {
                return cal.calculation(val1, val2);
            }

            @Override
            public M transformation(N values) {
                return trans.transformation(values);
            }
        };
    }

    public static <N> CalculationContainerFunction<N, N> crate(Calculation<N> calculation){
        return crate(v -> v, calculation);
    }

    public <N> CalculationContainerFunction<N, U> changeType(Transformation<N, U> trans){
        return crate(trans, this);
    }

    public CalculationContainerFunction<U, U> deleteTransformation(){
        return crate(this);
    }

}
