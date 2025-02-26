package kg.arbocdi.builder.manufacturing.events;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class StartManufacturingEvent extends ManufacturingEvent {
    private final String blueprintName;
    private final String materialStore;
    private final String productStore;

    public StartManufacturingEvent(String transactionId, String blueprintName, String materialStore, String productStore) {
        super(transactionId);
        this.blueprintName = blueprintName;
        this.materialStore = materialStore;
        this.productStore = productStore;
    }
}
