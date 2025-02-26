package kg.arbocdi.builder.api.blueprint;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class BlueprintView {
    @NotNull
    private String name;
    @NotNull
    private List<BlueprintMaterialView> inputMaterials = new LinkedList<>();
    @NotNull
    private List<BlueprintMaterialView> outputMaterials = new LinkedList<>();
}
