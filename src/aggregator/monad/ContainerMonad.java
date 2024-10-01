package aggregator.monad;

import aggregator.container.Container;
import aggregator.lambda.Conversion;

public class ContainerMonad <T, U> implements Container<T, U>{

    private final Container<T, U> function;

    private ContainerMonad(Container<T, U> container){
        function = container;
    }

    public static <N, M> ContainerMonad<N, M> from(Container<N, M> container){
        return new ContainerMonad<>(container);
    }

    public static <N, M> ContainerMonad<N, M> from(Conversion<N, M> conversion){
        return from((_, val) -> conversion.convert(val));
    }

    public <N> ContainerMonad<T, N> map(Container<U, N> container){
        return from((res, val) -> container.calculate(res, function.calculate(null, val)));
    }

    public <N> ContainerMonad<T, N> map(Conversion<U, N> conversion){
        return map((_, val) -> conversion.convert(val));
    }

    public <N> AggregatorMonad<T, N> mapAggregation(String name, Container<U, N[]> container, AggregatorMonad.AggregatorFunction<T, N[]> function){
        return AggregatorMonad.from(name, map(container), function);
    }

    @Override
    public U calculate(U result, T values) {
        return function.calculate(result, values);
    }

}
