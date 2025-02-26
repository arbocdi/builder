package kg.arbocdi.builder.manufacturing.events;

import kg.arbocdi.builder.cfg.domain.BuilderEvent;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class ManufacturingEvent extends BuilderEvent {
    public ManufacturingEvent(String transactionId) {
        super(transactionId);
    }
}
