package kg.arbocdi.builder.api.productStore.takeProduct;

import kg.arbocdi.builder.api.productStore.ProductStoreEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ProductTakenFromStoreEvent extends ProductStoreEvent {
    private final String name;
    private final int qty;
    public ProductTakenFromStoreEvent(TakeProductFromStoreCommand cmd) {
        super(cmd);
        name = cmd.getName();
        qty=cmd.getQty();
    }
}
