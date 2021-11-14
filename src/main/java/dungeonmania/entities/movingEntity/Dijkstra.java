package dungeonmania.entities.movingEntity;

import java.util.HashMap;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.staticEntity.*;
import dungeonmania.util.Position;

public interface Dijkstra {
    
   public default void dijksmove(Dungeon dungeon, Entity entity) {
    Player player = (Player) dungeon.getPlayer();
    HashMap<Position, Integer> Grid = Grid(dungeon, entity);
    HashMap<Position, Position> prev = dikjstra(Grid, player);
    Position curr = new Position(entity.getX(), entity.getY());
    Position next = prev.get(curr);

    entity.setX(next.getX());
    entity.setY(next.getY());

   }

public HashMap<Position, Position> dikjstra(HashMap<Position, Integer> Grid, Player player);

public default HashMap<Position, Integer> Grid(Dungeon dungeon, Entity entity) {
    HashMap<Position, Integer> grid = new HashMap<>();
        int maxX;
        int maxY;
        Player player = (Player) dungeon.getPlayer();
        if(player.getX() > entity.getX()) {maxX = player.getX();}
        else {maxX = entity.getX();}
        if(player.getY() > entity.getY()){maxY = player.getY();}
        else {maxY = entity.getY();};

        for(int i = 0; i < maxY; i++) {
            for(int j = 0; j < maxX; j++) {
                Position pos = new Position(j,i);
                int cost = 1;
                List<Entity> list = dungeon.getEntitiesOnSamePosition(pos);

                for(Entity current : list) {
                    if (current instanceof Wall) {
                        cost = 50;
                    }
                    if (current instanceof Boulder) {
                        cost = 10;
                    }
                    if (current instanceof SwampTile) {
                        SwampTile tile = (SwampTile) current;
                        cost = tile.getMovementFactor();
                    }
                }
                grid.put(pos, cost);

            }
        }
        return grid;
    }

}
}
