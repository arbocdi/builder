package kg.arbocdi.builder.views.products;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.productStore.ProductsStoreChangedEvent;
import kg.arbocdi.builder.api.productStore.create.ProductSoreCreatedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products_stores")
public class ProductsStoreEntity {
    @Id
    private String name;
    @NotNull
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<StoredProduct> products = new LinkedList<>();

    public ProductsStoreEntity(ProductSoreCreatedEvent event) {
        name = event.getId();
    }

    public ProductsStoreEntity(ProductsStoreChangedEvent event) {
        name = event.getView().getName();
        products = event.getView().getProducts().stream()
                .map(StoredProduct::new)
                .collect(Collectors.toList());
    }
}
