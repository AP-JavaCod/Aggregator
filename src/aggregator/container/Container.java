package aggregator.container;

public interface Container <T, U> {

    U calculate(U result, T values);

    default ModificationContainer<T, U> getModification(String name){
        return new ModificationContainer<>(name, this);
    }

}
