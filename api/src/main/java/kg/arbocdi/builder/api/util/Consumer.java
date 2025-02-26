package kg.arbocdi.builder.api.util;

public interface Consumer<T,E extends Exception> {
    void consume(T t) throws E;
}
