package aggregator.container;

import aggregator.lambda.Filter;

public abstract class ContainerIfElse<T, U> implements Container<T, U>, Filter<T> {

    @Override
    public U calculate(U result, T values) {
        return filter(values) ? trueCalculate(result, values) : falseCalculate(result, values);
    }

    protected abstract U trueCalculate(U result, T values);
    protected abstract U falseCalculate(U result, T values);

    public static <N, M> ContainerIfElse<N, M> getInstance(Filter<N> fil, Container<N, M> trueFunction, Container<N, M> falseFunction){
        return new ContainerIfElse<>() {
            @Override
            protected M trueCalculate(M result, N values) {
                return trueFunction.calculate(result, values);
            }

            @Override
            protected M falseCalculate(M result, N values) {
                return falseFunction.calculate(result, values);
            }

            @Override
            public boolean filter(N values) {
                return fil.filter(values);
            }
        };
    }

}
