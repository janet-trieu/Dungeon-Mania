package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;

public class Inventory {
    private List<CollectableEntity> items;
    
    /**
     * Constructor for Inventory
     */
    public Inventory() {
        items = new ArrayList<CollectableEntity>();
    }

    /**
     * item is added to inventory
     * only one key is allowed in inventory at a time
     * @param item
     * @return
     */
    public Boolean addItem(CollectableEntity item) {
        // if player picks up key but player already has a key, dont put key in inventory
        if (item instanceof Key && getKey() != null) {
            return false;
        }
        items.add(item);
        return true;
    }

    /**
     * item is removed from inventory
     * @param item
     */
    public void removeItem(CollectableEntity item) {
        items.remove(item);
    }

    /**
     * Method to count the number of items in inventory with the type
     * @param type
     * @return number of items with type
     */
    public int numberOfItem(String type) {
        int counter = 0;
        for (CollectableEntity item : items) {
            if (item.getType().equals(type)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * checks if inventory contains an entity
     * @param entity
     * @return
     */
    public Boolean contains(Entity entity) {
        return items.contains(entity);
    }

    /**
     * @return items inside inventory
     */
    public List<CollectableEntity> getItems() {
        return items;
    }

    /**
     * Method to retrieve a key from inventory
     * @return key from inventory, null otherwise
     */
    public Key getKey() {
        for (CollectableEntity entity : items) {
            if (entity instanceof Key) {
                Key key = (Key) entity;
                return key;
            }
        }
        return null;
    }

    /**
     * remove the first instance of an item with type
     * @param type
     */
    public void breakItem(String type) {
        for (CollectableEntity item : items) {
            if (item.getType().equals(type)) {
                removeItem(item);
                return;
            }
        }
    }

    @Override
    public String toString() {
        String string = "";
        for (CollectableEntity item : items) {
            string = string + item.getId();
        }
        return string;
    }
}
