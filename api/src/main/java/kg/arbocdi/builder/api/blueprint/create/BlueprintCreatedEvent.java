package kg.arbocdi.builder.api.blueprint.create;

import kg.arbocdi.builder.api.blueprint.BlueprintEvent;
import kg.arbocdi.builder.api.blueprint.BlueprintView;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class BlueprintCreatedEvent extends BlueprintEvent {
    private final BlueprintView blueprintView;
    public BlueprintCreatedEvent(CreateBlueprintCommand cmd) {
        super(cmd);
        this.blueprintView = cmd.getBlueprintView();
    }
}
