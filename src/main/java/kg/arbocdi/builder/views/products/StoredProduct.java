package kg.arbocdi.builder.views.products;

import kg.arbocdi.builder.api.productStore.StoredProductView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoredProduct {
    private String name;
    private int qty;

    public StoredProduct(StoredProductView view) {
        name = view.getName();
        qty = view.getQty();
    }
}
