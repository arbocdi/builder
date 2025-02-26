package kg.arbocdi.builder.api.productStore;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ProductStoreView {
    @NotNull
    private String name;
    @NotNull
    private List<StoredProductView> products = new LinkedList<>();
}
