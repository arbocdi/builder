package kg.arbocdi.builder.manufacturing;

import kg.arbocdi.builder.api.materialStore.addMaterial.AddMaterialToStoreCommand;
import kg.arbocdi.builder.api.materialStore.takeMaterial.MaterialTakenFromStoreEvent;
import kg.arbocdi.builder.api.materialStore.takeMaterial.TakeMaterialFromStoreCommand;
import kg.arbocdi.builder.api.productStore.addProduct.AddProductToStoreCommand;
import kg.arbocdi.builder.api.productStore.addProduct.ProductAddedToStoreEvent;
import kg.arbocdi.builder.api.productStore.takeProduct.TakeProductFromStoreCommand;
import kg.arbocdi.builder.cfg.axon.commandSaga.CommandSagaBase;
import kg.arbocdi.builder.manufacturing.events.ManufacturingStartedEvent;
import kg.arbocdi.builder.manufacturing.events.StartManufacturingEvent;
import kg.arbocdi.builder.views.blueprints.BlueprintEntity;
import kg.arbocdi.builder.views.blueprints.BlueprintEntityRepo;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Saga
@Slf4j
public class ManufacturingSaga extends CommandSagaBase {
    private String blueprintName;
    private BlueprintEntity blueprint;
    private String materialStore;
    private String productStore;

    private List<TakeMaterialFromStoreCommand> takeCommands = new LinkedList<>();
    private final List<AddMaterialToStoreCommand> takeCommandsRollback = new LinkedList<>();

    private List<AddProductToStoreCommand> addCommands = new LinkedList<>();
    private final List<TakeProductFromStoreCommand> addCommandsRollback = new LinkedList<>();

    @SagaEventHandler(associationProperty = "transactionId")
    @StartSaga
    public void on(StartManufacturingEvent event) throws Throwable {

        materialStore = event.getMaterialStore();
        productStore = event.getProductStore();
        blueprintName = event.getBlueprintName();

        send(new ManufacturingStartedEvent(event));
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(ManufacturingStartedEvent event, @Autowired BlueprintEntityRepo repo) throws Throwable {
        blueprint = repo.findById(blueprintName).get();
        takeCommands = blueprint.getInputMaterials().stream()
                .map(m -> new TakeMaterialFromStoreCommand(materialStore, m.getName(), m.getQty()))
                .collect(Collectors.toList());
        addCommands = blueprint.getOutputMaterials().stream()
                .map(m -> new AddProductToStoreCommand(productStore, m.getName(), m.getQty()))
                .collect(Collectors.toList());

        sendMaterialCommand(takeCommands.remove(0));
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MaterialTakenFromStoreEvent event) throws Throwable {
        if (!takeCommands.isEmpty()) {
            sendMaterialCommand(takeCommands.remove(0));
        } else {
            sendProductCommand(addCommands.remove(0));
        }
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(ProductAddedToStoreEvent event) throws Throwable {
        throw new RuntimeException("test error");
//        if (!addCommands.isEmpty()) {
//            sendProductCommand(addCommands.remove(0));
//        } else completeOk(null);
    }

    @Override
    protected void onError() {
        takeCommandsRollback.forEach(c -> sendAndSwallow(c));
        addCommandsRollback.forEach(c -> sendAndSwallow(c));
    }

    private void sendMaterialCommand(TakeMaterialFromStoreCommand cmd) throws Throwable {
        send(cmd);
        takeCommandsRollback.add(new AddMaterialToStoreCommand(cmd.getId(), cmd.getName(), cmd.getQty()));
    }

    private void sendProductCommand(AddProductToStoreCommand cmd) throws Throwable {
        send(cmd);
        addCommandsRollback.add(new TakeProductFromStoreCommand(cmd.getId(), cmd.getName(), cmd.getQty()));
    }
}
