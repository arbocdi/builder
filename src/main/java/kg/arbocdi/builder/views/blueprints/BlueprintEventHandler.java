package kg.arbocdi.builder.views.blueprints;

import kg.arbocdi.builder.api.blueprint.create.BlueprintCreatedEvent;
import kg.arbocdi.builder.api.blueprint.remove.BlueprintRemovedEvent;
import kg.arbocdi.builder.cfg.axon.AxonKafkaIntegrationConfig;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup(AxonKafkaIntegrationConfig.KAFKA_GROUP)
public class BlueprintEventHandler {
    @Autowired
    private BlueprintEntityRepo repo;

    @EventHandler
    public void on(BlueprintCreatedEvent event) {
        repo.save(new BlueprintEntity(event));
    }

    @EventHandler
    public void on(BlueprintRemovedEvent event) {
        repo.deleteById(event.getId());
    }
}
