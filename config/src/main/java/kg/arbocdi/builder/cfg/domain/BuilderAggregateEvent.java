package kg.arbocdi.builder.cfg.domain;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class BuilderAggregateEvent extends BuilderEvent{
    private final String id;
    public BuilderAggregateEvent(BuilderCommand cmd) {
        super(cmd.getTransactionId());
        id = cmd.getId();
    }
}
