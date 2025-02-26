package kg.arbocdi.builder.cfg.axon;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.extensions.springcloud.DistributedCommandBusProperties;
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor;
import org.axonframework.micrometer.GlobalMetricRegistry;
import org.axonframework.spring.config.SpringAxonConfiguration;
import org.axonframework.tracing.SpanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class AxonCommandBusConfig {

    @Autowired
    @Bean(destroyMethod = "shutdown")
    @Qualifier("localSegment")
    public CommandBus configureAsynchronousCommandBus(
            TransactionManager transactionManager,
            GlobalMetricRegistry metricRegistry,
            SpanFactory spanFactory) {
        AsynchronousCommandBus commandBus = AsynchronousCommandBus.builder()
                .transactionManager(transactionManager)
                .messageMonitor(metricRegistry.registerCommandBus("localCommandBus"))
                .spanFactory(spanFactory)
                .build();
        return commandBus;
    }

    @Bean
    @Autowired
    public ConfigurerModule commandBusCorrelationConfigurerModule(

    ) {
        return configurer -> configurer.onInitialize(
                config -> {
                    config.commandBus().registerHandlerInterceptor(new CorrelationDataInterceptor<>(config.correlationDataProviders()));

                }
        );
    }


}
