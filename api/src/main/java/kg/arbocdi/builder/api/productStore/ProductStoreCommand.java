package kg.arbocdi.builder.api.productStore;

import kg.arbocdi.builder.cfg.domain.BuilderCommand;
import lombok.ToString;

@ToString(callSuper = true)
public class ProductStoreCommand extends BuilderCommand {
    public ProductStoreCommand(String id) {
        super(id);
    }
}
