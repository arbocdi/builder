package kg.arbocdi.builder.api.productStore;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class ProductsStoreChangedEvent extends ProductStoreEvent{
    private final ProductStoreView view;
    public ProductsStoreChangedEvent(ProductStoreView view) {
        super(new ProductStoreCommand(view.getName()));
        this.view = view;
    }
}
