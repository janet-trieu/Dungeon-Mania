package dungeonmania;
import dungeonmania.util.*;
import dungeonmania.entities.movingEntity.MovingEntity;

interface Moveable {
    
    default void move(Direction direction, MovingEntity entity) {
        Position current = entity.getPosition();
        int x = current.getX();
        int y = current.getY();
        if(direction == Direction.NONE) {
            return;
        }

        if (direction == Direction.UP) {
            Position update = new Position(current.getX(), current.getY() - 1, current.getLayer());
            entity.setPosition
        }

    }
         

}
