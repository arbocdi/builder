package kg.arbocdi.builder.api.util;

import java.util.Optional;

public class OptionalResult<T,E extends Exception> {
    private final Optional<T> resultOpt;

    public OptionalResult(Optional<T> resultOpt) {
        this.resultOpt = resultOpt;
    }
    public OptionalResult<T,E> present(Consumer<T,E> consumer) throws E {
        if(resultOpt.isPresent()) consumer.consume(resultOpt.get());
        return this;
    }
    public OptionalResult<T,E> absent(Action<E> action) throws E {
        if(resultOpt.isEmpty()) action.perform();
        return this;
    }
    public OptionalResult<T,E> ifAbsentThrow(E exception) throws E {
        if(resultOpt.isEmpty())  throw exception;
        return this;
    }
}
