package kg.arbocdi.builder.cfg.axon;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.axonframework.common.jdbc.PersistenceExceptionResolver;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.micrometer.GlobalMetricRegistry;
import org.axonframework.modelling.saga.repository.jpa.JpaSagaStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.tracing.SpanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EntityScan(basePackageClasses = {JpaEventStorageEngine.class, org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore.class, JpaSagaStore.class})
public class AxonStorageConfig {
    @Bean
    public EventStore eventStore(EventStorageEngine storageEngine,
                                 SpanFactory spanFactory,
                                 GlobalMetricRegistry metricRegistry) {
        return EmbeddedEventStore.builder()

                .storageEngine(storageEngine)
                .messageMonitor(metricRegistry.registerEventBus("eventStore"))
                .spanFactory(spanFactory)
                // ...
                .build();
    }

    // The JpaEventStorageEngine stores events in a JPA-compatible data source.
    @Bean
    public EventStorageEngine eventStorageEngine(Serializer serializer,
                                                 PersistenceExceptionResolver persistenceExceptionResolver,
                                                 @Qualifier("eventSerializer") Serializer eventSerializer,
                                                 EntityManagerProvider entityManagerProvider,
                                                 TransactionManager transactionManager) {
        return JpaEventStorageEngine.builder()
                .snapshotSerializer(serializer)
                .persistenceExceptionResolver(persistenceExceptionResolver)
                .eventSerializer(eventSerializer)
                .entityManagerProvider(entityManagerProvider)
                .transactionManager(transactionManager)
                // ...
                .build();
    }

    @Bean
    public JpaSagaStore sagaStore(Serializer serializer, EntityManagerProvider entityManagerProvider) {
        return JpaSagaStore.builder()
                .entityManagerProvider(entityManagerProvider)
                .serializer(serializer)
                .build();
    }

    @Bean
    @Primary
    public Serializer defaultSerializer() {
        // Set the secure types on the xStream instance
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}
