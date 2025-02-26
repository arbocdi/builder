package kg.arbocdi.builder.views.materials;

import kg.arbocdi.builder.api.materialStore.StoredMaterialView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoredMaterial {
    private String name;
    private int qty;

    public StoredMaterial(StoredMaterialView view) {
        name = view.getName();
        qty = view.getQty();
    }
}
