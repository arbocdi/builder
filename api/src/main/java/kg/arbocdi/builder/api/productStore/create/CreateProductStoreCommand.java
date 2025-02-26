package kg.arbocdi.builder.api.productStore.create;

import kg.arbocdi.builder.api.blueprint.BlueprintCommand;
import kg.arbocdi.builder.api.blueprint.BlueprintView;
import kg.arbocdi.builder.api.productStore.ProductStoreCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CreateProductStoreCommand extends ProductStoreCommand {
    public CreateProductStoreCommand(String name) {
        super(name);
    }
}
