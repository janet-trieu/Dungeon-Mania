package dungeonmania.entities.collectableEntity.buildableEntity;

import java.util.List;
import java.util.ListIterator;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.entities.collectableEntity.Arrow;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.collectableEntity.SunStone;
import dungeonmania.entities.collectableEntity.Treasure;
import dungeonmania.entities.collectableEntity.Wood;

public class Sceptre extends BuildableEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // dungeon instance
    Dungeon currDungeon = Dungeon.getDungeon();

    /**
     * Constructor for a sceptre
     * @param x position
     * @param y position 
     */
    public Sceptre(int x, int y) {
        super(x, y, "sceptre");
        setId("Sceptre" + String.valueOf(counter));
        counter++;
    }

    @Override
    public void useIngredient() {
        Inventory currInventory = currDungeon.getInventory();
        List<CollectableEntity> items = currInventory.getItems();
        ListIterator<CollectableEntity> iter = items.listIterator();
        List<String> buildableList = currDungeon.getBuildableString();

        // Can be crafted with one wood or two arrows, one key or treasure, and one sun stone.
        // usage of arrows is prioritised over usage of wood
        int woodCounter = 0;
        int arrowCounter = 0;
        int keyCounter = 0;
        int treasureCounter = 0;
        int sunStoneCounter = 0;
        for (CollectableEntity item : items) {
            if (item instanceof Wood) {
                woodCounter++;
            } else if (item instanceof Arrow) {
                arrowCounter++;
            } else if (item instanceof Key) {
                keyCounter++;
            } else if (item instanceof Treasure) {
                treasureCounter++;
            } else if (item instanceof SunStone) {
                sunStoneCounter++;
            }
        }

        // use 2 arrows, if cannot, use wood
        if (arrowCounter >= 2) {
            int i = 0;
            while (iter.hasNext()) {
                CollectableEntity current = (CollectableEntity)iter.next();
                if (i == 2) {
                    break;
                }
                if (current instanceof Arrow) {
                    iter.remove();
                    i++;
                }
            }
        } else if (woodCounter >= 1) {
            items.remove(currInventory.invGetInstance("wood"));
        }

        // use treasure if cannot, use key, if cannot, use sun stone
        if (treasureCounter >= 1) {
            items.remove(currInventory.invGetInstance("treasure"));
        } else if (keyCounter >= 1) {
            items.remove(currInventory.invGetInstance("key"));
        } else if (sunStoneCounter >= 2) {
            items.remove(currInventory.invGetInstance("sun_stone"));
        }

        // use 1 sun stone
        if (sunStoneCounter >= 1) {
            items.remove(currInventory.invGetInstance("sun_stone"));

            // update the buildable list, as sceptre has now been built
            buildableList.remove("sceptre");
        }
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Sceptre.counter = counter;
    }

}
