package kg.arbocdi.builder.api.materialStore.addMaterial;

import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.materialStore.MaterialStoreEvent;
import kg.arbocdi.builder.api.materialStore.MaterialStoreView;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class MaterialAddedToStoreEvent extends MaterialStoreEvent {
    @NotNull
    private final String name;
    private final int qty;
    public MaterialAddedToStoreEvent(AddMaterialToStoreCommand cmd) {
        super(cmd);
        name = cmd.getName();
        qty=cmd.getQty();
    }
}
