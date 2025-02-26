package kg.arbocdi.builder.views.blueprints;

import kg.arbocdi.builder.api.blueprint.BlueprintMaterialView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlueprintMaterial {
    private String name;
    private int qty;

    public BlueprintMaterial(BlueprintMaterialView view) {
        name = view.getName();
        qty = view.getQty();
    }
}
