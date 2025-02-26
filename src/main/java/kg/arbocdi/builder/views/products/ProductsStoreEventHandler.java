package kg.arbocdi.builder.views.products;

import kg.arbocdi.builder.api.productStore.ProductsStoreChangedEvent;
import kg.arbocdi.builder.api.productStore.create.ProductSoreCreatedEvent;
import kg.arbocdi.builder.api.productStore.remove.ProductStoreRemovedEvent;
import kg.arbocdi.builder.cfg.axon.AxonKafkaIntegrationConfig;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup(AxonKafkaIntegrationConfig.KAFKA_GROUP)
public class ProductsStoreEventHandler {
    @Autowired
    private ProductsStoreRepo repo;

    @EventHandler
    public void on(ProductSoreCreatedEvent event) {
        repo.save(new ProductsStoreEntity(event));
    }

    @EventHandler
    public void on(ProductStoreRemovedEvent event) {
        repo.deleteById(event.getId());
    }

    @EventHandler
    public void on(ProductsStoreChangedEvent event) {
        repo.save(new ProductsStoreEntity(event));
    }
}
