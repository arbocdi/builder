package kg.arbocdi.builder.api.materialStore.addMaterial;

import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.materialStore.MaterialStoreCommand;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class AddMaterialToStoreCommand extends MaterialStoreCommand {
    @NotNull
    private final String name;
    private final int qty;
    public AddMaterialToStoreCommand(String id, String name, int qty) {
        super(id);
        this.name = name;
        this.qty = qty;
    }
}
