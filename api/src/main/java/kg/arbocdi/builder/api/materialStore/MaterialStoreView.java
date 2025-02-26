package kg.arbocdi.builder.api.materialStore;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;

@Data
public class MaterialStoreView {
    @NotNull
    private String name;
    @NotNull
    private List<StoredMaterialView> materials = new LinkedList<>();
}
