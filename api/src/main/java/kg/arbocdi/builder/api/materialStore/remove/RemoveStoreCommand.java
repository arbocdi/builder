package kg.arbocdi.builder.api.materialStore.remove;

import kg.arbocdi.builder.api.materialStore.MaterialStoreCommand;
import kg.arbocdi.builder.api.materialStore.MaterialStoreView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@Setter
public class RemoveStoreCommand extends MaterialStoreCommand {

    public RemoveStoreCommand(String name) {
        super(name);
    }
}
