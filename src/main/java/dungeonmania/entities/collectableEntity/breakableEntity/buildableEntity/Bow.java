package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.collectableEntity.Arrow;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Wood;
import dungeonmania.util.Position;

public class Bow extends BuildableEntity {

    // durability attribute of bow is set to 3 (ticks)
    private int durability = 3;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

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
        List<CollectableEntity> inventory = Dungeon.getDungeon().getInventory().getItems();
        List<String> buildableList = Dungeon.getDungeon().getBuildableString();
    
        // used up 3 arrows and 1 wood, so remove them from inventory
        int arrowCounter = 0;
        for (CollectableEntity ingredient : inventory) {
            if (arrowCounter == 3) {
                break;
            }
            if (ingredient instanceof Arrow) {
                inventory.remove(ingredient);
                arrowCounter++;
            }
        }
        for (CollectableEntity ingredient : inventory) {
            if (ingredient instanceof Wood) {
                inventory.remove(ingredient);
                break;
            }
        }
        buildableList.remove("bow");
    }

}
