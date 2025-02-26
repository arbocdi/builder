package kg.arbocdi.builder;

import kg.arbocdi.builder.api.blueprint.BlueprintMaterialView;
import kg.arbocdi.builder.api.blueprint.BlueprintView;
import kg.arbocdi.builder.api.blueprint.create.CreateBlueprintCommand;
import kg.arbocdi.builder.api.materialStore.addMaterial.AddMaterialToStoreCommand;
import kg.arbocdi.builder.api.materialStore.create.CreateStoreCommand;
import kg.arbocdi.builder.api.materialStore.takeMaterial.TakeMaterialFromStoreCommand;
import kg.arbocdi.builder.api.productStore.addProduct.AddProductToStoreCommand;
import kg.arbocdi.builder.api.productStore.create.CreateProductStoreCommand;
import kg.arbocdi.builder.cfg.domain.BuilderCommand;
import kg.arbocdi.builder.cfg.spring.IdGenerator;
import kg.arbocdi.builder.manufacturing.events.StartManufacturingEvent;
import kg.arbocdi.builder.views.blueprints.BlueprintEntity;
import kg.arbocdi.builder.views.blueprints.BlueprintEntityRepo;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class BuilderService {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private EventGateway eventGateway;
    @Autowired
    private BlueprintEntityRepo blueprintEntityRepo;
    @Autowired
    private IdGenerator idGenerator;

    public List init() {
        List results = new LinkedList();
        results.addAll(createStorages());
        results.addAll(createBlueprints());
        results.addAll(createMaterials());
        return results;
    }

    public List build() throws InterruptedException {
//        int cruisers = 1000;
//        int battleships = 200;
//
        List results = new LinkedList();
//        results.addAll(createStorages());
//        results.addAll(createBlueprints());
//        results.addAll(createMaterials());
//
//        Thread.sleep(10_000);

        eventGateway.publish(new StartManufacturingEvent(
                idGenerator.generate(),
                "battleship",
                "jita",
                "amarr"
        ));

//        long start = System.currentTimeMillis();
//        for(int i=0;i<cruisers;i++){
//            results.addAll(build("cruiser"));
//        }
//        for(int i=0;i<battleships;i++){
//            results.addAll(build("battleship"));
//        }
//        long end = System.currentTimeMillis();
//        double time = (end-start)/1_000D;
//        int commands = (battleships+cruisers)*4;
//        double throughput=commands/time;
//        log.info("execution time {}",(end-start)/1_000D);
//        results.add(" commands = "+commands+" time "+time+" throughput "+throughput);
        return results;
    }

    public List createStorages() {
        List results = new LinkedList();
        CreateProductStoreCommand createProductStoreCommand = new CreateProductStoreCommand("amarr");
        execute(createProductStoreCommand, results);
        return results;
    }

    public List createBlueprints() {
        List results = new LinkedList();

        BlueprintView blueprintView = new BlueprintView();
        blueprintView.setName("cruiser");
        blueprintView.getInputMaterials().add(new BlueprintMaterialView("armorPlate", 1));
        blueprintView.getInputMaterials().add(new BlueprintMaterialView("engine", 1));
        blueprintView.getInputMaterials().add(new BlueprintMaterialView("turret", 1));

        blueprintView.getOutputMaterials().add(new BlueprintMaterialView("cruiser", 1));
        execute(new CreateBlueprintCommand(blueprintView), results);

        blueprintView = new BlueprintView();
        blueprintView.setName("battleship");
        blueprintView.getInputMaterials().add(new BlueprintMaterialView("armorPlate", 10));
        blueprintView.getInputMaterials().add(new BlueprintMaterialView("engine", 10));
        blueprintView.getInputMaterials().add(new BlueprintMaterialView("turret", 10));

        blueprintView.getOutputMaterials().add(new BlueprintMaterialView("battleship", 1));
        execute(new CreateBlueprintCommand(blueprintView), results);

        return results;
    }

    public List createMaterials() {
        List<Object> results = new LinkedList<>();
        CreateStoreCommand createStoreCommand = new CreateStoreCommand("jita");
        execute(createStoreCommand, results);

        int qty = 1000_000;

        AddMaterialToStoreCommand addMaterialToStoreCommand = new AddMaterialToStoreCommand(
                createStoreCommand.getId(),
                "armorPlate",
                qty
        );
        execute(addMaterialToStoreCommand, results);

        addMaterialToStoreCommand = new AddMaterialToStoreCommand(
                createStoreCommand.getId(),
                "engine",
                qty
        );
        execute(addMaterialToStoreCommand, results);

        addMaterialToStoreCommand = new AddMaterialToStoreCommand(
                createStoreCommand.getId(),
                "turret",
                qty
        );
        execute(addMaterialToStoreCommand, results);
        return results;
    }

    public List build(String name) {
        BlueprintEntity blueprint = blueprintEntityRepo.findById(name).get();
        List results = new LinkedList();
        blueprint.getInputMaterials().stream()
                .map(m -> new TakeMaterialFromStoreCommand("jita", m.getName(), m.getQty()))
                .forEach(c -> execute(c, results));
        blueprint.getOutputMaterials().stream()
                .map(m -> new AddProductToStoreCommand("amarr", m.getName(), m.getQty()))
                .forEach(c -> execute(c, results));
        return results;
    }


    protected void execute(BuilderCommand cmd, List results) {
        try {
            results.add(commandGateway.send(cmd).get());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
