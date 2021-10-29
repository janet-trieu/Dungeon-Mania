package dungeonmania.entities.movingEntity;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;


public interface Moveable {
    
    default void move(Direction direction, Entity entity) {
        
        Dungeon dungeon = Dungeon.getDungeon();
        Position move = entity.getPosition().translateBy(direction);
        String type = entity.getType();
        
        List<Entity> list = dungeon.getEntitiesOnSamePosition(move);
        
        for(Entity current : list) {
            if (current.getType() == "boulder" || current.getType() == "wall") {
                if(type == "mercenary" || type == "zombie_toast") {
                    return;
                }
            }
        }
        
        entity.setPosition(move.getX(), move.getY());
    }


    

}
