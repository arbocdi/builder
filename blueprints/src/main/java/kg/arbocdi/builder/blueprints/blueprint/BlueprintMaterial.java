package kg.arbocdi.builder.blueprints.blueprint;

import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.blueprint.BlueprintMaterialView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlueprintMaterial {
    @NotNull
    private String name;
    private int qty;

    public BlueprintMaterial(BlueprintMaterialView view){
        name = view.getName();
        qty= view.getQty();;
    }

    public BlueprintMaterialView toView(){
        return new BlueprintMaterialView()
                .setName(name)
                .setQty(qty);
    }
}
