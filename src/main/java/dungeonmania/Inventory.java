package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;

public class Inventory {
    private List<CollectableEntity> items;
    
    public Inventory() {
        items = new ArrayList<CollectableEntity>();
    }

    public Boolean addItem(CollectableEntity item) {
        // if player picks up key but player already has a key, dont put key in inventory
        if (item instanceof Key && getKey() != null) {
            return false;
        }
        items.add(item);
        return true;
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

    public List<CollectableEntity> getItems() {
        return items;
    }

    public Key getKey() {
        for (CollectableEntity entity : items) {
            if (entity instanceof Key) {
                Key key = (Key) entity;
                return key;
            }
        }
        return null;
    }
}
