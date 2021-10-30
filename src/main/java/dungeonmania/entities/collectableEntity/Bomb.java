package dungeonmania.entities.collectableEntity;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Bomb extends CollectableEntity {
    
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
     * Assumption: this method will only be called when a switch is activated next to this bomb
     * @param bomb
     */
    public void explode(Bomb bomb) {
        List<Position> bombAdjacentPositions = bomb.getPosition().getAdjacentPositions();
        List<Entity> existingEntities = Dungeon.getDungeon().getEntityList();

        // loop through the bomb's adjacent positions 
        for (Entity thisEntity : existingEntities) {
            if (bombAdjacentPositions.contains(thisEntity.getPosition())) {
                // it should get deleted
                Dungeon.getDungeon().getEntityList().remove(thisEntity);
            }
        }
    }

}
