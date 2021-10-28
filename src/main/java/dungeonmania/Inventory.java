package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.CollectableEntity;

public class Inventory {
    private List<CollectableEntity> items;
    
    public Inventory() {
        items = new ArrayList<CollectableEntity>();
    }

    public void addItem(CollectableEntity item) {
        items.add(item);
    }

    public void removeItem(CollectableEntity item) {
        items.remove(item);
    }

    public int numberOfItem(String type) {
        int counter = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getType() == type) {
                counter++;
            }
        }
        return counter;
    }

    public Boolean contains(Entity entity) {
        return items.contains(entity);
    }

    

}
