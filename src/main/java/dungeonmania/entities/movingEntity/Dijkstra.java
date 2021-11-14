package dungeonmania.entities.movingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

public default HashMap<Position, Position> dikjstra(HashMap<Position, Integer> Grid, Player player) {
    HashMap<Position, Integer> dist = new HashMap<>();
    HashMap<Position, Position> prev = new HashMap<>();
    Queue<Position> queue = new LinkedList<>();
    Position playerPos = new Position(player.getX(), player.getY());
    queue.add(playerPos);
    dist.put(playerPos, 0);

    for(Position pos : Grid.keySet()) {
        if (pos != playerPos) {
            dist.put(pos, Integer.MAX_VALUE);
            prev.put(pos, null);
                
        }
    } 
    
        
    while(!queue.isEmpty()) {
        Position u = queue.peek();
        queue.remove(u);
        for(Position v : CardAdj(u)) {
            if(Grid.containsKey(v)){
                queue.add(v);
                if (dist.get(u) + Grid.get(v) <= dist.get(v)) {
                    dist.put(v, dist.get(u)+ Grid.get(v)); 
                    prev.put(v, u); 
                }
            }
        }
    }
    return prev;
}



public default HashMap<Position, Integer> Grid(Dungeon dungeon, Entity entity) {
    HashMap<Position, Integer> grid = new HashMap<>();
        int maxX;
        int maxY;
        Player player = (Player) dungeon.getPlayer();
        if(player.getX() > entity.getX()) {maxX = player.getX();}
        else {maxX = entity.getX();}
        if(player.getY() > entity.getY()){maxY = player.getY();}
        else {maxY = entity.getY();};

        for(int i = 0; i <= maxY; i++) {
            for(int j = 0; j <= maxX; j++) {
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

    public default List<Position> CardAdj(Position pos) {
        List<Position> CardAdj = new ArrayList<>();
        CardAdj.add(new Position(pos.getX(), pos.getY() - 1));
        CardAdj.add(new Position(pos.getX(), pos.getY() + 1));
        CardAdj.add(new Position(pos.getX() - 1, pos.getY()));
        CardAdj.add(new Position(pos.getX() + 1, pos.getY()));
        
        return CardAdj;
    }



}
