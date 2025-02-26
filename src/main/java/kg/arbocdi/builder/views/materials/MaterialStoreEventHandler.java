package kg.arbocdi.builder.views.materials;

import kg.arbocdi.builder.api.materialStore.MaterialStoreChangedEvent;
import kg.arbocdi.builder.api.materialStore.create.StoreCreatedEvent;
import kg.arbocdi.builder.api.materialStore.remove.StoreRemovedEvent;
import kg.arbocdi.builder.cfg.axon.AxonKafkaIntegrationConfig;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup(AxonKafkaIntegrationConfig.KAFKA_GROUP)
public class MaterialStoreEventHandler {
    @Autowired
    private MaterialStoreRepo repo;

    @EventHandler
    public void on(StoreCreatedEvent createdEvent) {
        repo.save(new MaterialStoreEntity(createdEvent));
    }

    @EventHandler
    public void on(StoreRemovedEvent removedEvent) {
        repo.deleteById(removedEvent.getId());
    }

    @EventHandler
    public void on(MaterialStoreChangedEvent evt) {
        repo.save(new MaterialStoreEntity(evt));
    }
}
