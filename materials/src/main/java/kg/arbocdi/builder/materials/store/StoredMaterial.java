package kg.arbocdi.builder.materials.store;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.arbocdi.builder.api.materialStore.StoredMaterialView;
import kg.arbocdi.builder.api.materialStore.addMaterial.MaterialAddedToStoreEvent;
import kg.arbocdi.builder.api.materialStore.takeMaterial.MaterialTakenFromStoreEvent;
import kg.arbocdi.builder.cfg.domain.NotEnoughException;
import kg.arbocdi.builder.api.materialStore.takeMaterial.TakeMaterialFromStoreCommand;
import kg.arbocdi.builder.cfg.spring.IdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@Table(name="stored_materials")
@NoArgsConstructor
public class StoredMaterial {
    @Id
    private String id;
    @NotNull
    private String materialStoreName;
    @NotNull
    private String name;
    private int qty;

    public StoredMaterial(MaterialAddedToStoreEvent evt, IdGenerator idGenerator){
        id = idGenerator.generate();
        materialStoreName=evt.getId();
        name=evt.getName();
        qty=evt.getQty();
    }

    public void on(MaterialAddedToStoreEvent evt){
        qty = qty+evt.getQty();
    }

    public void on(TakeMaterialFromStoreCommand cmd) throws NotEnoughException {
        if(qty<cmd.getQty()) throw new NotEnoughException(cmd.getId(),cmd.getName());
    }

    public void on(MaterialTakenFromStoreEvent evt){
        qty=qty-evt.getQty();
    }

    public StoredMaterialView toView(){
        return new StoredMaterialView()
                .setName(name)
                .setQty(qty);
    }

    public boolean nameEquals(String name){
        return Objects.equals(this.name,name);
    }
}
