package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

import java.util.List;
import java.util.ListIterator;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Wood;

public class Shield extends BuildableEntity {

    // durability attribute of shield is set to 5 (ticks)
    private int durability = 5;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // dungeon instance
    Dungeon currDungeon = Dungeon.getDungeon();

    /**
     * Constructor for a shield
     * @param x position
     * @param y position
     */
    public Shield(int x, int y) {
        super(x, y, "shield");
        setId("Shield" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }

    @Override
    public void useIngredient() {
        Inventory currInventory = currDungeon.getInventory();
        List<CollectableEntity> items = currInventory.getItems();
        ListIterator<CollectableEntity> iter = items.listIterator();
        List<String> buildableList = currDungeon.getBuildableString();

        // used up 2 wood + (1 sun stone OR 1 treasure OR 1 key)
        int woodCounter = 0;
        while (iter.hasNext()) {
            CollectableEntity current = (CollectableEntity)iter.next();
            if (woodCounter == 2) {
                break;
            }
            if (current instanceof Wood) {
                iter.remove();
                woodCounter++;
            }
        }

        // use of treasure is prioritised over use of key for creation of shield (assumption)
        // use of sun stone would be the last choice
        if (currInventory.invGetInstance("treasure") != null) {
            items.remove(currInventory.invGetInstance("treasure"));
        } else if (currInventory.invGetInstance("key") != null) {
            items.remove(currInventory.invGetInstance("key"));
        } else if (currInventory.invGetInstance("sun_stone") != null) {
            items.remove(currInventory.invGetInstance("sun_stone"));
        }

        // update the buildable list
        buildableList.remove("shield");
    }
    
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Shield.counter = counter;
    }

}
