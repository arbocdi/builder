package kg.arbocdi.builder.cfg.axon;

import javassist.bytecode.ByteArray;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonKafkaIntegrationConfig {
    public static final String  KAFKA_GROUP = "kafka-group";
    @Autowired
    public void configureStreamableKafkaSource(
            EventProcessingConfigurer configurer,
            StreamableKafkaMessageSource<String, ByteArray> streamableKafkaMessageSource
    ) {
        configurer.registerTrackingEventProcessor(KAFKA_GROUP,configuration -> streamableKafkaMessageSource);
    }
}
