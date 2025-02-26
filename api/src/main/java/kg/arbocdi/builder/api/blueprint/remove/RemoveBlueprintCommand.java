package kg.arbocdi.builder.api.blueprint.remove;

import kg.arbocdi.builder.api.blueprint.BlueprintCommand;
import kg.arbocdi.builder.api.blueprint.BlueprintView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RemoveBlueprintCommand extends BlueprintCommand {
    public RemoveBlueprintCommand(String name) {
        super(name);
    }
}
