package kg.arbocdi.builder.cfg.domain;

import lombok.Data;

@Data
public class BuilderEvent {
    private final String transactionId;

    public BuilderEvent(String transactionId) {
        this.transactionId = transactionId;
    }
}
