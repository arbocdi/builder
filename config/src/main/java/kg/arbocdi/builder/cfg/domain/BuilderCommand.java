package kg.arbocdi.builder.cfg.domain;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class BuilderCommand {
    @TargetAggregateIdentifier
    private final String id;
    private String transactionId;
}
