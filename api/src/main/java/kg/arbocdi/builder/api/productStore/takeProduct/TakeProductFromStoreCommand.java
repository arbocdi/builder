package kg.arbocdi.builder.api.productStore.takeProduct;

import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.productStore.ProductStoreCommand;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class TakeProductFromStoreCommand extends ProductStoreCommand {
    @NotNull
    private final String name;
    private final int qty;
    public TakeProductFromStoreCommand(String id, String name, int qty) {
        super(id);
        this.name = name;
        this.qty = qty;
    }
}
