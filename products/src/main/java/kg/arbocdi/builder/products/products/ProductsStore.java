package kg.arbocdi.builder.products.products;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.productStore.ProductStoreView;
import kg.arbocdi.builder.api.productStore.ProductsStoreChangedEvent;
import kg.arbocdi.builder.api.productStore.addProduct.AddProductToStoreCommand;
import kg.arbocdi.builder.api.productStore.addProduct.ProductAddedToStoreEvent;
import kg.arbocdi.builder.api.productStore.create.CreateProductStoreCommand;
import kg.arbocdi.builder.api.productStore.create.ProductSoreCreatedEvent;
import kg.arbocdi.builder.api.productStore.remove.ProductStoreRemovedEvent;
import kg.arbocdi.builder.api.productStore.remove.RemoveProductStoreCommand;
import kg.arbocdi.builder.api.productStore.takeProduct.ProductTakenFromStoreEvent;
import kg.arbocdi.builder.api.productStore.takeProduct.TakeProductFromStoreCommand;
import kg.arbocdi.builder.api.util.OptionalResult;
import kg.arbocdi.builder.cfg.domain.BuilderAggregateException;
import kg.arbocdi.builder.cfg.domain.NotEnoughException;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Aggregate
@Entity
@Table(name = "product_stores")
@NoArgsConstructor
public class ProductsStore {
    @NotNull
    @AggregateIdentifier
    @Id
    private String name;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    @NotNull
    List<StoredProduct> storedProducts = new LinkedList<>();

    public ProductStoreView toView() {
        return new ProductStoreView()
                .setName(name)
                .setProducts(storedProducts.stream().map(StoredProduct::toView).collect(Collectors.toList()));
    }

    @CommandHandler(routingKey = "id")
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public ProductStoreView on(CreateProductStoreCommand cmd) {
        AggregateLifecycle.apply(new ProductSoreCreatedEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(ProductSoreCreatedEvent event) {
        name = event.getId();
    }

    @CommandHandler(routingKey = "id")
    public ProductStoreView on(RemoveProductStoreCommand cmd) {
        AggregateLifecycle.apply(new ProductStoreRemovedEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(ProductStoreRemovedEvent event, @Autowired EntityManager em) throws BuilderAggregateException {
        em.remove(this);
    }

    @CommandHandler(routingKey = "id")
    public ProductStoreView on(AddProductToStoreCommand cmd) {
        AggregateLifecycle.apply(new ProductAddedToStoreEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(ProductAddedToStoreEvent event) throws BuilderAggregateException {
        findProduct(event.getName())
                .present(m -> m.on(event))
                .absent(() -> storedProducts.add(new StoredProduct(event)));
        AggregateLifecycle.apply(new ProductsStoreChangedEvent(toView()));
    }

    @CommandHandler(routingKey = "id")
    public ProductStoreView on(TakeProductFromStoreCommand cmd) throws BuilderAggregateException {
        findProduct(cmd.getName())
                .present(p -> p.on(cmd))
                .ifAbsentThrow(new NotEnoughException(cmd.getId(), cmd.getName()));
        AggregateLifecycle.apply(new ProductTakenFromStoreEvent(cmd));
        return toView();
    }


    @EventHandler
    public void on(ProductTakenFromStoreEvent event) throws BuilderAggregateException {
        findProduct(event.getName())
                .present(m -> m.on(event));
        AggregateLifecycle.apply(new ProductsStoreChangedEvent(toView()));
    }

    protected OptionalResult<StoredProduct, BuilderAggregateException> findProduct(String name) {
        return new OptionalResult<>(
                storedProducts.stream()
                        .filter(m -> m.nameEquals(name))
                        .findFirst()
        );
    }
}
