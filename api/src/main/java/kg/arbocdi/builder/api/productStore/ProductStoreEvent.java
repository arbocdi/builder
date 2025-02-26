package kg.arbocdi.builder.api.productStore;

import kg.arbocdi.builder.cfg.domain.BuilderAggregateEvent;
import lombok.ToString;

@ToString(callSuper = true)
public class ProductStoreEvent extends BuilderAggregateEvent {
    public ProductStoreEvent(ProductStoreCommand cmd) {
        super(cmd);
    }
}
