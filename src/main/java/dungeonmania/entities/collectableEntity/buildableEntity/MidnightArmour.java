package dungeonmania.entities.collectableEntity.buildableEntity;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.entities.collectableEntity.Armour;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.SunStone;

public class MidnightArmour extends BuildableEntity {

    // TODO: change damage and protection later
    public final static int protection = 10;
    public final static int damage = 10;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // dungeon instance
    Dungeon currDungeon = Dungeon.getDungeon();

    /**
     * Constructor for a sceptre
     * @param x position
     * @param y position 
     */
    public MidnightArmour(int x, int y) {
        super(x, y, "midnight_armour");
        setId("MidnightArmour" + String.valueOf(counter));
        counter++;
    }

    @Override
    public void useIngredient() {
        Inventory currInventory = currDungeon.getInventory();
        List<CollectableEntity> items = currInventory.getItems();
        List<String> buildableList = currDungeon.getBuildableString();

        int armourCounter = 0;
        int sunStoneCounter = 0;
        for (CollectableEntity item : items) {
            if (item instanceof Armour) {
                armourCounter++;
            } else if (item instanceof SunStone) {
                sunStoneCounter++;
            }
        }
        // Can be crafted with one armour and one sun stone
        if (armourCounter > 0 && sunStoneCounter > 0) {
            items.remove(currInventory.invGetInstance("armour"));
            items.remove(currInventory.invGetInstance("sun_stone"));
            // update the buildable list, as midnight armour has now been built
            buildableList.remove("midnight_armour");
        }
    }

    public static void setCounter(int counter) {
        MidnightArmour.counter = counter;
    }
    
}
