package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

import java.util.List;
import java.util.ListIterator;

import dungeonmania.Dungeon;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.collectableEntity.Treasure;
import dungeonmania.entities.collectableEntity.Wood;

public class Shield extends BuildableEntity {

    // durability attribute of shield is set to 5 (ticks)
    private int durability = 5;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

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
        List<CollectableEntity> inventory = Dungeon.getDungeon().getInventory().getItems();
        ListIterator<CollectableEntity> iter = inventory.listIterator();
        List<String> buildableList = Dungeon.getDungeon().getBuildableString();

        // used up 2 wood + (1 treasure OR 1 key)
        int woodCounter = 0;
        while (iter.hasNext()) {
            CollectableEntity current = (CollectableEntity)iter.next();
            if (woodCounter == 1) {
                break;
            }
            if (current instanceof Wood) {
                iter.remove();
                woodCounter++;
            }
        }

        // use of treasure is prioritised over use of key for creation of shield (assumption)
        for (CollectableEntity ingredient : inventory) {
            if (ingredient instanceof Treasure) {
                inventory.remove(ingredient);
                break;
            } else if (ingredient instanceof Key) {
                inventory.remove(ingredient);
                break; 
            }
        }

        // update the buildable list
        buildableList.remove("shield");
    }

}
