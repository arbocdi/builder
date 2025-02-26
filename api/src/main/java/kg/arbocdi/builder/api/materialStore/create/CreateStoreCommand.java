package kg.arbocdi.builder.api.materialStore.create;

import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.materialStore.MaterialStoreCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@Setter
public class CreateStoreCommand extends MaterialStoreCommand {
    public CreateStoreCommand(String name) {
        super(name);
    }
}
