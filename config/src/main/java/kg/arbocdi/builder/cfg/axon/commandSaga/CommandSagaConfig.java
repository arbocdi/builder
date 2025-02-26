package kg.arbocdi.builder.cfg.axon.commandSaga;

import javassist.bytecode.ByteArray;
import org.axonframework.config.Configurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource;
import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource;
import org.axonframework.spring.stereotype.Saga;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandSagaConfig {
    @Autowired
    public void trackingProcessorConfigurerModule(Configurer configurer, StreamableKafkaMessageSource<String, ByteArray> messageSource) {
        Reflections reflections = new Reflections("kg.arbocdi");
        reflections.getSubTypesOf(CommandSagaBase.class)
                .stream()
                .filter(sagabaseClass -> sagabaseClass.isAnnotationPresent(Saga.class))
                .forEach(aClass -> {
                    String group = aClass.getName() + "Custom";
                    configurer.eventProcessing(
                            eventProcessingConfigurer -> eventProcessingConfigurer.assignHandlerTypesMatching(group, aClass1 -> aClass1 == aClass)
                    );

                    configurer.eventProcessing(
                            processingConfigurer -> processingConfigurer.registerTrackingEventProcessor(
                                    group,
                                    conf -> messageSource
                            ).registerListenerInvocationErrorHandler(
                                    group,
                                    configuration -> PropagatingErrorHandler.instance()
                            ).registerErrorHandler(
                                    group,
                                    configuration -> PropagatingErrorHandler.instance())
                    );
                });
    }
}
