package kg.arbocdi.builder.products.products;

import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.productStore.StoredProductView;
import kg.arbocdi.builder.api.productStore.addProduct.ProductAddedToStoreEvent;
import kg.arbocdi.builder.api.productStore.takeProduct.ProductTakenFromStoreEvent;
import kg.arbocdi.builder.api.productStore.takeProduct.TakeProductFromStoreCommand;
import kg.arbocdi.builder.cfg.domain.NotEnoughException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class StoredProduct {
    @NotNull
    private String name;
    private int qty;

    public StoredProduct(ProductAddedToStoreEvent event) {
        name = event.getName();
        qty = event.getQty();
        ;
    }

    public StoredProductView toView() {
        return new StoredProductView()
                .setName(name)
                .setQty(qty);
    }

    public void on(ProductAddedToStoreEvent event) {
        qty = qty + event.getQty();
    }

    public void on(TakeProductFromStoreCommand cmd) throws NotEnoughException {
        if (qty < cmd.getQty()) throw new NotEnoughException(cmd.getId(), cmd.getName());
    }

    public void on(ProductTakenFromStoreEvent event) {
        qty = qty - event.getQty();
    }

    public boolean nameEquals(String name) {
        return Objects.equals(this.name, name);
    }

}
