package kg.arbocdi.builder.views.blueprints;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.blueprint.create.BlueprintCreatedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "blueprints")
public class BlueprintEntity {
    @Id
    private String name;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @NotNull
    private List<BlueprintMaterial> inputMaterials = new LinkedList<>();
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @NotNull
    private List<BlueprintMaterial> outputMaterials = new LinkedList<>();

    public BlueprintEntity(BlueprintCreatedEvent event) {
        name = event.getBlueprintView().getName();
        inputMaterials = event.getBlueprintView().getInputMaterials().stream()
                .map(BlueprintMaterial::new)
                .collect(Collectors.toList());
        outputMaterials = event.getBlueprintView().getOutputMaterials().stream()
                .map(BlueprintMaterial::new)
                .collect(Collectors.toList());

    }
}
