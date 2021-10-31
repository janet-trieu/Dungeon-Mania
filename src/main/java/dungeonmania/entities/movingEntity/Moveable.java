package dungeonmania.entities.movingEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Wall;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;


public interface Moveable {
    
    /**
     * Default move method for all moving entities excluding player
     * @param direction
     * @param entity
     */
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

    /**
     * Returns list of distance between adjacent positions of entities and player's positions
     * @param entity
     * @param dungeon
     * @return
     */
    default List<Integer> Path(Entity entity, Dungeon dungeon) {
        Entity player = dungeon.getPlayer();
        Position up = entity.getPosition().translateBy(Direction.UP);
        Position down = entity.getPosition().translateBy(Direction.DOWN);
        Position left = entity.getPosition().translateBy(Direction.LEFT);
        Position right = entity.getPosition().translateBy(Direction.RIGHT);
        List<Position> movement = new ArrayList<>();
        movement.add(entity.getPosition());
        movement.add(up);
        movement.add(down);
        movement.add(left);
        movement.add(right); 

        List<Integer> distance = new ArrayList<>();

        for (Position curr : movement) {
            distance.add(Math.abs(curr.getX() - player.getX()) + Math.abs(curr.getY() - player.getY()));
        }

        return distance;
    }

    /**
     * Method for the moving entities to "run away" if player is invincible
     * @param entity
     * @param dungeon
     */
    default void run(Entity entity, Dungeon dungeon) {
        Player player = (Player) dungeon.getPlayer();
        List<Integer> distance = Path(entity, dungeon);
        Direction move = Direction.NONE;
        int index = distance.indexOf(Collections.max(distance));
        if(index == 0) {
            return;
        }
        if(index == 1) {
            move = Direction.UP;
        }
        if(index == 2) {
            move = Direction.DOWN;
        }
        if(index == 3) {
            move = Direction.LEFT;
        }
        if(index == 4) {
            move = Direction.RIGHT;
        }
        Position next = entity.getPosition().translateBy(move);
        
        List<Entity> list = dungeon.getEntitiesOnSamePosition(next);
        
        //check if entity runs into a wall or boulder;
        for(Entity current : list) {
            if (current instanceof Boulder) {
                if(entity instanceof Mercenary) {
                    player.interactBoulder(current, next, move);
                }
                if(entity instanceof Spider || entity instanceof ZombieToast) {
                    return;
                }
            }

            if (current instanceof Wall) {
                if(entity instanceof ZombieToast || entity instanceof Mercenary )
                return;
            }
        }

        entity.setX(next.getX());
        entity.setY(next.getY());
    }
    
}
