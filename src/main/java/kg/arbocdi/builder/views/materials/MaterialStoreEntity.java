package kg.arbocdi.builder.views.materials;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.materialStore.MaterialStoreChangedEvent;
import kg.arbocdi.builder.api.materialStore.create.StoreCreatedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "material_stores")
@NoArgsConstructor
public class MaterialStoreEntity {
    @Id
    private String name;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @NotNull
    private List<StoredMaterial> materials = new LinkedList<>();

    public MaterialStoreEntity(StoreCreatedEvent createdEvent) {
        name = createdEvent.getId();
    }

    public MaterialStoreEntity(MaterialStoreChangedEvent event) {
        name = event.getView().getName();
        materials = event.getView().getMaterials().stream()
                .map(StoredMaterial::new)
                .collect(Collectors.toList());
    }
}
