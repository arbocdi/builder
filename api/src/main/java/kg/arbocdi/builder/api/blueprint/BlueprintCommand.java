package kg.arbocdi.builder.api.blueprint;

import kg.arbocdi.builder.cfg.domain.BuilderCommand;
import lombok.ToString;

@ToString(callSuper = true)
public class BlueprintCommand extends BuilderCommand {
    public BlueprintCommand(String name) {
        super(name);
    }
}
