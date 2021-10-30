package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

import java.util.List;

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
        List<CollectableEntity> ingredientList = Dungeon.getDungeon().getInventory().getItems();
        List<String> buildableList = Dungeon.getDungeon().getBuildableString();

        // used up 2 wood + (1 treasure OR 1 key)
        int woodCounter = 0;
        for (CollectableEntity ingredient : ingredientList) {
            if (woodCounter == 1) {
                break;
            }
            if (ingredient instanceof Wood) {
                ingredientList.remove(ingredient);
                woodCounter++;
            }
        }
        // use of treasure is prioritised over use of key for creation of shield (assumption)
        for (CollectableEntity ingredient : ingredientList) {
            if (ingredient instanceof Treasure) {
                ingredientList.remove(ingredient);
                break;
            } else if (ingredient instanceof Key) {
                ingredientList.remove(ingredient);
                break; 
            }
        }
        buildableList.remove("shield");
    }

}
