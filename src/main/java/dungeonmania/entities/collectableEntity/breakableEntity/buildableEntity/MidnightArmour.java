package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.entities.collectableEntity.CollectableEntity;

public class MidnightArmour extends BuildableEntity {

    // durability attribute of midnight armour is set to 16 (ticks)
    public final static int durability = 16;
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

        // Can be crafted with one armour and one sun stone
        if (currInventory.invGetInstance("treasure") != null && currInventory.invGetInstance("sun_stone") != null) {
            items.remove(currInventory.invGetInstance("treasure"));
            items.remove(currInventory.invGetInstance("sun_stone"));

            // update the buildable list, as midnight armour has now been built
            buildableList.remove("midnight_armour");
        } 
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        MidnightArmour.counter = counter;
    }
    
}
