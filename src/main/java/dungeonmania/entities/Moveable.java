package dungeonmania.entities;
import dungeonmania.util.*;

interface Moveable {
    
    /**
     * Given an entity and direction set update the entity's position
     * @param direction
     * @param entity
     */
    default void move(Direction direction, Entity entity) {
        
        int x = entity.getX();
        int y = entity.getY();

        if (direction == Direction.NONE) {return;}
        if (direction == Direction.UP) {y = y - 1;}
        if (direction == Direction.DOWN) {y = y + 1;}
        if (direction == Direction.LEFT) {x = x - 1;}
        if (direction == Direction.RIGHT) {x = x + 1;}

        entity.setX(x);
        entity.setY(y);

    }
         
}
