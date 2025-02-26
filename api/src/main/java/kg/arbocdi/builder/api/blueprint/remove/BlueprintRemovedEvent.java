package kg.arbocdi.builder.api.blueprint.remove;

import kg.arbocdi.builder.api.blueprint.BlueprintEvent;
import kg.arbocdi.builder.api.blueprint.BlueprintView;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class BlueprintRemovedEvent extends BlueprintEvent {
    public BlueprintRemovedEvent(RemoveBlueprintCommand cmd) {
        super(cmd);
    }
}
