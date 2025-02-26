package kg.arbocdi.builder.views.products;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsStoreRepo extends JpaRepository<ProductsStoreEntity, String> {
}
