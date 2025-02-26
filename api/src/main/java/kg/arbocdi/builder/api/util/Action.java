package kg.arbocdi.builder.api.util;

public interface Action<E extends Exception> {
    void perform() throws E;
}
