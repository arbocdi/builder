package kg.arbocdi.builder.manufacturing.events;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ManufacturingStartedEvent extends ManufacturingEvent {
    public ManufacturingStartedEvent(StartManufacturingEvent event) {
        super(event.getTransactionId());
    }
}
