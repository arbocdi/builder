package kg.arbocdi.builder.api.materialStore;

import kg.arbocdi.builder.cfg.domain.BuilderCommand;
import lombok.ToString;

@ToString(callSuper = true)
public class MaterialStoreCommand extends BuilderCommand {
    public MaterialStoreCommand(String id) {
        super(id);
    }
}
