package dungeonmania.entities.staticEntity;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity {
    
    // boulder will always be in layer 1
    private int layer = 1;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a boulder
     * @param x position
     * @param y position
     */
    public Boulder(int x, int y) {
        super(x, y, "boulder");
        setId("Boulder" + String.valueOf(counter));
        counter++;
        setLayer(layer);
    }

    /**
     * Method for the movement of boulder
     * @param currPos
     * @param direction
     */
    public void push(Position currPos, Direction direction) {
        Position newPos = currPos.translateBy(direction);
        List<Entity> existingEntities = Dungeon.getDungeon().getEntityList();

        for (Entity entity : existingEntities) {
            // if the new position of boulder is the same cell as floor switch
            if (entity.getType().equals("switch")) {
                if (newPos.equals(entity.getPosition())) {
                    // set floor switch isActive = true
                    FloorSwitch thisEntity = (FloorSwitch)entity;
                    thisEntity.setIsActive(true);
                }
            // if the new position of boulder is in the same cell as another static entity, remain in current position    
            } else {
                if (newPos.equals(entity.getPosition())) {
                    newPos = currPos;
                }
            }
        }
        setPosition(newPos.getX(), newPos.getY());
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Boulder.counter = counter;
    }
    
}
