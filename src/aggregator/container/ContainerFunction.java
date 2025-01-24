package aggregator.container;

public interface ContainerFunction <T, U> {

    U apply(U result, T values);

}
