package aggregator.text;

import aggregator.Aggregator;

import java.util.List;

public abstract class TextFormatAggregator<T, U> implements Aggregator<T, String> {


    protected abstract String formatText(String result);
    protected abstract U aggregationTo(List<T> values);

}
