package kg.arbocdi.builder.api.blueprint;

import kg.arbocdi.builder.cfg.domain.BuilderAggregateEvent;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class BlueprintEvent extends BuilderAggregateEvent {
    public BlueprintEvent(BlueprintCommand cmd) {
        super(cmd);
    }
}
