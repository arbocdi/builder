package kg.arbocdi.builder.api.blueprint.create;

import kg.arbocdi.builder.api.blueprint.BlueprintCommand;
import kg.arbocdi.builder.api.blueprint.BlueprintView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CreateBlueprintCommand extends BlueprintCommand {
    private final BlueprintView blueprintView;
    public CreateBlueprintCommand(BlueprintView blueprintView) {
        super(blueprintView.getName());
        this.blueprintView = blueprintView;
    }
}
