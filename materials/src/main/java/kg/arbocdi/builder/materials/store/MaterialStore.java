package kg.arbocdi.builder.materials.store;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.cfg.domain.BuilderAggregateException;
import kg.arbocdi.builder.cfg.domain.NotEnoughException;
import kg.arbocdi.builder.api.materialStore.MaterialStoreChangedEvent;
import kg.arbocdi.builder.api.materialStore.MaterialStoreView;
import kg.arbocdi.builder.api.materialStore.addMaterial.AddMaterialToStoreCommand;
import kg.arbocdi.builder.api.materialStore.addMaterial.MaterialAddedToStoreEvent;
import kg.arbocdi.builder.api.materialStore.create.CreateStoreCommand;
import kg.arbocdi.builder.api.materialStore.create.StoreCreatedEvent;
import kg.arbocdi.builder.api.materialStore.remove.RemoveStoreCommand;
import kg.arbocdi.builder.api.materialStore.remove.StoreRemovedEvent;
import kg.arbocdi.builder.api.materialStore.takeMaterial.MaterialTakenFromStoreEvent;
import kg.arbocdi.builder.api.materialStore.takeMaterial.TakeMaterialFromStoreCommand;
import kg.arbocdi.builder.api.util.OptionalResult;
import kg.arbocdi.builder.cfg.spring.IdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "material_stores")
@Aggregate
@NoArgsConstructor
public class MaterialStore {
    @Id
    @AggregateIdentifier
    private String name;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "materialStoreName", referencedColumnName = "name", updatable = false)
    private List<StoredMaterial> materials = new LinkedList<>();

    public MaterialStoreView toView() {
        return new MaterialStoreView()
                .setName(name)
                .setMaterials(materials.stream().map(StoredMaterial::toView).collect(Collectors.toList()));
    }

    @CommandHandler(routingKey = "id")
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public MaterialStoreView on(CreateStoreCommand cmd) {
        AggregateLifecycle.apply(new StoreCreatedEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(StoreCreatedEvent event) {
        name = event.getId();
    }

    @CommandHandler(routingKey = "id")
    public MaterialStoreView on(AddMaterialToStoreCommand cmd) {
        AggregateLifecycle.apply(new MaterialAddedToStoreEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(MaterialAddedToStoreEvent event,@Autowired IdGenerator idGenerator) throws BuilderAggregateException {
        findMaterial(event.getName())
                .present(m -> m.on(event))
                .absent(() -> materials.add(new StoredMaterial(event, idGenerator)));
        AggregateLifecycle.apply(new MaterialStoreChangedEvent(toView()));
    }

    @CommandHandler(routingKey = "id")
    public MaterialStoreView on(TakeMaterialFromStoreCommand cmd) throws BuilderAggregateException {
        findMaterial(cmd.getName())
                .present(m -> m.on(cmd))
                .ifAbsentThrow(new NotEnoughException(cmd.getId(), cmd.getName()));
        AggregateLifecycle.apply(new MaterialTakenFromStoreEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(MaterialTakenFromStoreEvent event) throws BuilderAggregateException {
        findMaterial(event.getName())
                .present(m -> m.on(event));
        AggregateLifecycle.apply(new MaterialStoreChangedEvent(toView()));
    }

    @CommandHandler(routingKey = "id")
    public MaterialStoreView on(RemoveStoreCommand cmd) {
        AggregateLifecycle.apply(new StoreRemovedEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(StoreRemovedEvent event, @Autowired EntityManager em) {
        em.remove(this);
    }

    protected OptionalResult<StoredMaterial, BuilderAggregateException> findMaterial(String name) {
        return new OptionalResult<>(
                materials.stream()
                        .filter(m -> m.nameEquals(name))
                        .findFirst()
        );
    }
}
