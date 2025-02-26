package kg.arbocdi.builder.cfg.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class BuilderAggregateException extends BuilderException {
    private final String id;
    public BuilderAggregateException(String id, String msg) {
        super(msg);
        this.id = id;
    }
}
