package dungeonmania.entities.collectableEntity;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.Exit;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.entities.staticEntity.ZombieToastSpawner;

public class Bomb extends CollectableEntity {

    //
    Dungeon currDungeon = Dungeon.getDungeon();
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a bomb
     * @param x position
     * @param y position
     */
    public Bomb(int x, int y) {
        super(x, y, "bomb");
        setId("Bomb" + String.valueOf(counter));
        counter++;
    }

    /**
     * Method to achieve the bomb explosion
     * @precondition this method will only be called when a switch is activated adjacent to this bomb
     */
    public void explode() {
        List<Entity> adjacentEntities = currDungeon.adjacentEntityList(this);
        List<Entity> existingEntities = currDungeon.getEntityList();

        for (Entity entity : adjacentEntities) {
            if (!(entity instanceof Player || entity instanceof Portal || entity instanceof ZombieToastSpawner 
                || entity instanceof Door || entity instanceof Exit)) {

                // delete entities    
                existingEntities.remove(entity);
            }
        }
        // remove bomb after explostion
        existingEntities.remove(this);
    }
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Bomb.counter = counter;
    }
}
