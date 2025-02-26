package kg.arbocdi.builder.api.productStore.create;

import kg.arbocdi.builder.api.blueprint.BlueprintEvent;
import kg.arbocdi.builder.api.blueprint.BlueprintView;
import kg.arbocdi.builder.api.productStore.ProductStoreEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ProductSoreCreatedEvent extends ProductStoreEvent {
    public ProductSoreCreatedEvent(CreateProductStoreCommand cmd) {
        super(cmd);
    }
}
