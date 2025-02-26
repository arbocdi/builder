package kg.arbocdi.builder.cfg.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class NotEnoughException extends BuilderAggregateException {
    private final String name;
    public NotEnoughException(String id, String name) {
        super(id, "Not enough");
        this.name = name;
    }
}
