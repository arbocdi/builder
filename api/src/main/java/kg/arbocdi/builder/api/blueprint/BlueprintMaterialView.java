package kg.arbocdi.builder.api.blueprint;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlueprintMaterialView {
    @NotNull
    private String name;
    private int qty;
}
