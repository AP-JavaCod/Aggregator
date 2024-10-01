package aggregator.monad;

import aggregator.Aggregator;
import aggregator.container.Container;

public class AggregatorMonad<T, U> implements Aggregator<T, U[]> {

    private final Aggregator<T, U[]> aggregator;

    private AggregatorMonad(Aggregator<T, U[]> aggregator){
        this.aggregator = aggregator;
    }

   public static <N, M> AggregatorMonad<N, M> from(Aggregator<N, M[]> aggregator){
        return new AggregatorMonad<>(aggregator);
   }

   public static <N, M> AggregatorMonad<N, M> from(String name, Container<N, M[]> container, AggregatorFunction<N, M[]> function){
       return new AggregatorMonad<>(function.apply(name, container));
   }

   public <N> AggregatorMonad<T, N> map(Aggregator<U, N[]> fx){
        Aggregator<T, N[]> fun = new Aggregator<>() {
            @Override
            public N[] aggregation(T[] values) {
                return fx.aggregation(aggregator.aggregation(values));
            }

            @Override
            public String getName() {
                return aggregator.getName() + "::" + fx.getName();
            }
        };
        return new AggregatorMonad<>(fun);
   }

   public <N> AggregatorMonad<T, N> map(String name, Container<U, N[]> container, AggregatorFunction<U, N[]> function){
        return map(function.apply(name, container));
   }

   public <N> Aggregator<T, N> finish(Aggregator<U, N> fx){
        return new Aggregator<>() {
            @Override
            public N aggregation(T[] values) {
                return fx.aggregation(aggregator.aggregation(values));
            }

            @Override
            public String getName() {
                return aggregator.getName() + "::" + fx.getName();
            }
        };
   }

   public <N> Aggregator<T, N> finish(String name, Container<U, N> container, AggregatorFunction<U, N> function){
        return finish(function.apply(name, container));
   }

    @Override
    public U[] aggregation(T[] values) {
        return aggregator.aggregation(values);
    }

    @Override
    public String getName() {
        return aggregator.getName();
    }

    public interface AggregatorFunction<Q, W> {

        Aggregator<Q, W> apply(String name, Container<Q, W> container);

    }

}
