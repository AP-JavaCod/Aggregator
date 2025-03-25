package aggregator.text;

import aggregator.Aggregator;

import java.util.List;

public abstract class StringAggregator <T, U> extends TextFormatAggregator<T, U> {

    private final String NAME;
    private final String FORMAT;

    public StringAggregator(String name){
        NAME = name;
        FORMAT = "%s: %s";
    }

    public StringAggregator(String name, String format){
        NAME = name;
        FORMAT = format;
    }

    @Override
    public final String aggregation(List<T> values) {
        return formatText(aggregationTo(values).toString());
    }

    public static <N, M> StringAggregator<N, M> crate(String name, Aggregator<N, M> aggregator){
        return crate(name, "%s: %s", aggregator);
    }

    public static <N, M> StringAggregator<N, M> crate(String name, String format, Aggregator<N, M> aggregator){
        return new StringAggregator<N, M>(name, format) {
            @Override
            protected M aggregationTo(List<N> values) {
                return aggregator.aggregation(values);
            }
        };
    }

    @Override
    protected String formatText(String result) {
        return String.format(FORMAT, NAME, result);
    }

}
