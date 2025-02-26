package kg.arbocdi.builder.api.productStore.remove;

import kg.arbocdi.builder.api.blueprint.BlueprintEvent;
import kg.arbocdi.builder.api.blueprint.BlueprintView;
import kg.arbocdi.builder.api.productStore.ProductStoreEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
public class ProductStoreRemovedEvent extends ProductStoreEvent {
    public ProductStoreRemovedEvent(RemoveProductStoreCommand cmd) {
        super(cmd);
    }
}
