package kg.arbocdi.builder.blueprints.blueprint;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.cfg.domain.BuilderAggregateException;
import kg.arbocdi.builder.api.blueprint.BlueprintView;
import kg.arbocdi.builder.api.blueprint.create.BlueprintCreatedEvent;
import kg.arbocdi.builder.api.blueprint.create.CreateBlueprintCommand;
import kg.arbocdi.builder.api.blueprint.remove.BlueprintRemovedEvent;
import kg.arbocdi.builder.api.blueprint.remove.RemoveBlueprintCommand;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Aggregate
@Entity
@Table(name = "blueprints")
@NoArgsConstructor
public class Blueprint {
    @NotNull
    @AggregateIdentifier
    @Id
    private String name;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @NotNull
    List<BlueprintMaterial> inputMaterials = new LinkedList<>();
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @NotNull
    List<BlueprintMaterial> outputMaterials = new LinkedList<>();

    public BlueprintView toView() {
        return new BlueprintView()
                .setName(name)
                .setInputMaterials(inputMaterials.stream().map(BlueprintMaterial::toView).collect(Collectors.toList()))
                .setOutputMaterials(outputMaterials.stream().map(BlueprintMaterial::toView).collect(Collectors.toList()));
    }

    @CommandHandler(routingKey = "id")
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public BlueprintView on(CreateBlueprintCommand cmd) {
        AggregateLifecycle.apply(new BlueprintCreatedEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(BlueprintCreatedEvent event) {
        name = event.getBlueprintView().getName();
        inputMaterials=event.getBlueprintView().getInputMaterials().stream().map(BlueprintMaterial::new).collect(Collectors.toList());
        outputMaterials=event.getBlueprintView().getOutputMaterials().stream().map(BlueprintMaterial::new).collect(Collectors.toList());
    }

    @CommandHandler(routingKey = "id")
    public BlueprintView on(RemoveBlueprintCommand cmd) {
        AggregateLifecycle.apply(new BlueprintRemovedEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(BlueprintRemovedEvent event,@Autowired EntityManager em) throws BuilderAggregateException {
        em.remove(this);
    }
}
