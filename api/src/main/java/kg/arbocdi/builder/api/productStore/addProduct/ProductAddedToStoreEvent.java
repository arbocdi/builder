package kg.arbocdi.builder.api.productStore.addProduct;

import kg.arbocdi.builder.api.materialStore.MaterialStoreEvent;
import kg.arbocdi.builder.api.productStore.ProductStoreEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ProductAddedToStoreEvent extends ProductStoreEvent {
    private final String name;
    private final int qty;
    public ProductAddedToStoreEvent(AddProductToStoreCommand cmd) {
        super(cmd);
        name = cmd.getName();
        qty=cmd.getQty();
    }
}
