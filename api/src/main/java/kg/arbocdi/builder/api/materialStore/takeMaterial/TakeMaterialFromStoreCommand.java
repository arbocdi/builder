package kg.arbocdi.builder.api.materialStore.takeMaterial;

import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.materialStore.MaterialStoreCommand;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class TakeMaterialFromStoreCommand extends MaterialStoreCommand {
    @NotNull
    private final String name;
    private final int qty;
    public TakeMaterialFromStoreCommand(String id, String name, int qty) {
        super(id);
        this.name = name;
        this.qty = qty;
    }
}
