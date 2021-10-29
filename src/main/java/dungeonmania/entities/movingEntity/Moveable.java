package dungeonmania.entities.movingEntity;
import dungeonmania.entities.Entity;
import dungeonmania.util.*;


public interface Moveable {
    
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
