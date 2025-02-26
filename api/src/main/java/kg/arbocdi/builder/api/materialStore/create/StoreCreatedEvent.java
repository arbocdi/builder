package kg.arbocdi.builder.api.materialStore.create;

import kg.arbocdi.builder.api.materialStore.MaterialStoreEvent;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class StoreCreatedEvent extends MaterialStoreEvent {
    public StoreCreatedEvent(CreateStoreCommand cmd) {
        super(cmd);
    }
}
