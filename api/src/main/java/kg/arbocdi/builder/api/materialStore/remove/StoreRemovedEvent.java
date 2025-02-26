package kg.arbocdi.builder.api.materialStore.remove;

import kg.arbocdi.builder.api.materialStore.MaterialStoreEvent;
import kg.arbocdi.builder.api.materialStore.MaterialStoreView;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class StoreRemovedEvent extends MaterialStoreEvent {
    public StoreRemovedEvent(RemoveStoreCommand cmd) {
        super(cmd);
    }
}
