package aggregator.modification.adapter;

import aggregator.Aggregator;
import aggregator.modification.AggregatorIterator;

import java.util.Iterator;

public class AdapterIterator <T, U> implements Aggregator<T, U> {

    private final AggregatorIterator<T, U> AGGREGATOR;

    public AdapterIterator(AggregatorIterator<T, U> aggregator){
        AGGREGATOR = aggregator;
    }

    public U aggregation(){
        U result = null;
        for (U val: AGGREGATOR){
            result = val;
        }
        return result;
    }

    @Override
    public U aggregation(T[] values) {
        U result = null;
        Iterator<U> iterator = AGGREGATOR.iterator(values);
        while (iterator.hasNext()){
            result = iterator.next();
        }
        return result;
    }

    @Override
    public String getName() {
        return AGGREGATOR.getName();
    }

    public String aggregationString(){
        return AGGREGATOR.getName() + ": " + aggregation();
    }

}
