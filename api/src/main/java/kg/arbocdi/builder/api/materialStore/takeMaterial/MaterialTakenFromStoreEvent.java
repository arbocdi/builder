package kg.arbocdi.builder.api.materialStore.takeMaterial;

import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.materialStore.MaterialStoreEvent;
import kg.arbocdi.builder.api.materialStore.MaterialStoreView;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class MaterialTakenFromStoreEvent extends MaterialStoreEvent {
    @NotNull
    private final String name;
    private final int qty;
    public MaterialTakenFromStoreEvent(TakeMaterialFromStoreCommand cmd) {
        super(cmd);
        name = cmd.getName();
        qty=cmd.getQty();
    }
}
