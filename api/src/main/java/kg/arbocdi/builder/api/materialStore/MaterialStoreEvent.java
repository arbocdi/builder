package kg.arbocdi.builder.api.materialStore;

import kg.arbocdi.builder.cfg.domain.BuilderAggregateEvent;
import lombok.ToString;

@ToString(callSuper = true)
public class MaterialStoreEvent extends BuilderAggregateEvent {
    public MaterialStoreEvent(MaterialStoreCommand cmd) {
        super(cmd);
    }
}
