package kg.arbocdi.builder.api.productStore.addProduct;

import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.materialStore.MaterialStoreCommand;
import kg.arbocdi.builder.api.productStore.ProductStoreCommand;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class AddProductToStoreCommand extends ProductStoreCommand {
    @NotNull
    private final String name;
    private final int qty;
    public AddProductToStoreCommand(String id, String name, int qty) {
        super(id);
        this.name = name;
        this.qty = qty;
    }
}
