package aggregator;

import java.util.Arrays;
import java.util.List;

public interface Aggregator <T, U> {

    U aggregation(List<T> values);

    default U aggregation(T... values){
        return aggregation(Arrays.asList(values));
    }

}
