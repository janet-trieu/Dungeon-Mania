package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

import java.util.List;
import java.util.ListIterator;

import dungeonmania.Dungeon;
import dungeonmania.entities.collectableEntity.Arrow;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Wood;

public class Bow extends BuildableEntity {

    // durability attribute of bow is set to 3 (ticks)
    private int durability = 3;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // dungeon instance
    Dungeon currDungeon = Dungeon.getDungeon();

    /**
     * Constructor for a bow
     * @param x position
     * @param y position 
     */
    public Bow(int x, int y) {
        super(x, y, "bow");
        setId("Bow" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }

    @Override
    public void useIngredient() {
        List<CollectableEntity> inventory =  currDungeon.getInventory().getItems();
        ListIterator<CollectableEntity> iter = inventory.listIterator();
        List<String> buildableList = currDungeon.getBuildableString();
    
        // used up 3 arrows and 1 wood, so remove them from inventory
        int arrowCounter = 0;
        while (iter.hasNext()) {
            CollectableEntity current = (CollectableEntity)iter.next();
            if (arrowCounter == 3) {
                break;
            }
            if (current instanceof Arrow) {
                iter.remove();
                arrowCounter++;
            }
        }
        
        for (CollectableEntity ingredient : inventory) {
            if (ingredient instanceof Wood) {
                inventory.remove(ingredient);
                break;
            }
        }
        
        // update the buildable list 
        buildableList.remove("bow");
    }
    
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Bow.counter = counter;
    }

}
