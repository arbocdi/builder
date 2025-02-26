package kg.arbocdi.builder.api.productStore;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoredProductView {
    @NotNull
    private String name;
    private int qty;
}
