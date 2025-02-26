package kg.arbocdi.builder.api.materialStore;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class MaterialStoreChangedEvent extends MaterialStoreEvent {
    private final MaterialStoreView view;

    public MaterialStoreChangedEvent(MaterialStoreView view) {
        super(new MaterialStoreCommand(view.getName()));
        this.view = view;
    }
}
