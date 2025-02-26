package kg.arbocdi.builder.api.productStore.remove;

import kg.arbocdi.builder.api.blueprint.BlueprintCommand;
import kg.arbocdi.builder.api.productStore.ProductStoreCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RemoveProductStoreCommand extends ProductStoreCommand {
    public RemoveProductStoreCommand(String name) {
        super(name);
    }
}
