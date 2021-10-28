package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.CollectableEntity;

public class Inventory {
    private List<CollectableEntity> items = new ArrayList<CollectableEntity>();
    
    public Inventory() {
        // TODO
    }

    public void addItem(CollectableEntity item) {
        //TODO
    }

    public void removeItem(CollectableEntity item) {
        //TODO
    }

    public List<CollectableEntity> getItems() {
        return items;
    }

    public void setItems(List<CollectableEntity> items) {
        this.items = items;
    }
}
