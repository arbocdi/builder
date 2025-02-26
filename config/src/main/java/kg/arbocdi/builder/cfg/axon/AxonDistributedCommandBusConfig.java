package kg.arbocdi.builder.cfg.axon;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.distributed.RoutingStrategy;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.extensions.springcloud.DistributedCommandBusProperties;
import org.axonframework.extensions.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.extensions.springcloud.commandhandling.mode.CapabilityDiscoveryMode;
import org.axonframework.micrometer.GlobalMetricRegistry;
import org.axonframework.serialization.Serializer;
import org.axonframework.tracing.SpanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonDistributedCommandBusConfig {
    private final DistributedCommandBusProperties properties;
    private final DistributedCommandBusProperties.SpringCloudProperties springCloudProperties;
    public AxonDistributedCommandBusConfig(DistributedCommandBusProperties properties) {
        this.properties = properties;
        this.springCloudProperties = properties.getSpringCloud();
    }
    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient,
                                                  Registration localServiceInstance,
                                                  RoutingStrategy routingStrategy,
                                                  CapabilityDiscoveryMode capabilityDiscoveryMode,
                                                  Serializer serializer) {
        return SpringCloudCommandRouter.builder()
                .discoveryClient(discoveryClient)
                .localServiceInstance(localServiceInstance)
                .routingStrategy(routingStrategy)
                .capabilityDiscoveryMode(capabilityDiscoveryMode)
                .serializer(serializer)
                .serviceInstanceFilter(serviceInstance -> !serviceInstance.getServiceId().contains("consul"))
                .build();
    }
    @Bean
    @Primary
    public DistributedCommandBus distributedCommandBus(CommandRouter commandRouter,
                                                       CommandBusConnector commandBusConnector,
                                                       GlobalMetricRegistry metricRegistry,
                                                       SpanFactory spanFactory) {
        DistributedCommandBus commandBus = DistributedCommandBus.builder()
                .commandRouter(commandRouter)
                .connector(commandBusConnector)
                .messageMonitor(metricRegistry.registerCommandBus("commandBus"))
                .spanFactory(spanFactory)
                .build();
        commandBus.updateLoadFactor(properties.getLoadFactor());
        return commandBus;
    }
    @Bean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return DefaultCommandGateway.builder().commandBus(commandBus).build();
    }

}
