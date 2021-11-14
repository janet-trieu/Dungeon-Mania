package dungeonmania.entities.movingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.staticEntity.*;
import dungeonmania.util.Position;

public interface Dijkstra {
    
    /**
     * Returns a map based on entitys position relative to player
     * and the cost of moving to a position
     * @param dungeon
     * @param entity
     * @return
     */
    public default HashMap<Position, Double> grid(Dungeon dungeon, Entity entity) {
        HashMap<Position, Double> grid = new HashMap<>();
        Player player = (Player) dungeon.getPlayer();
        int maxX;
        int maxY;

        if (player.getX() > entity.getX()) {maxX = player.getX();} 
        else {maxX = entity.getX();}
        if (player.getY() > entity.getY()) {maxY = player.getY();}
        else {maxY = entity.getY();}

        for(int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                Double cost = 1.0;
                Position point = new Position(j,i);
                List<Entity> type = dungeon.getEntitiesOnSamePosition(point);

                for (Entity curr : type) {
                    if (curr instanceof Wall) {cost = 10000.0;}
                    if (curr instanceof Boulder) {cost = 50.0;}
                    if (curr instanceof Portal) {cost = 1000.0;}
                    if (curr instanceof SwampTile) {
                        SwampTile tile = (SwampTile) curr;
                        cost = Double.valueOf(tile.getMovementFactor());
                    }
                }
                grid.put(point, cost);
            }
        }
        return grid;
    }

    /**
     * Returns a map of positions, and their previous position with shortest path
     * @param dungeon
     * @param entity
     * @return
     */
    public default Map<Position, Position> dijkstra(Dungeon dungeon, Entity entity) {
        Player player = (Player) dungeon.getPlayer();
        Position posP = new Position(player.getX(), player.getY());

        HashMap<Position, Double> grid = grid(dungeon, entity);

        Map<Position, Double> dist = new HashMap<>();
        Map<Position, Position> prev = new HashMap<>();
        Queue<Position> queue = new LinkedList<>();
        
        queue.add(posP);
        for(Position point : grid.keySet()) {
            dist.put(point, Double.POSITIVE_INFINITY);
        }
        dist.put(posP, 0.0);

        while(!queue.isEmpty()) {
            Position u = queue.element();
            queue.remove();
            List<Position> neighbours = neighbour(u);
            
            for (Position v : neighbours) {
                if(grid.containsKey(v)) {
                    if(prev.get(v) == null) {
                        queue.add(v);
                    }
                    if(dist.get(u) + grid.get(v) < dist.get(v)) {
                        dist.put(v, dist.get(u) + grid.get(v));
                        prev.put(v,u);
                    }
                }
            }
        }


        return prev;
    }
    /**
     * Returns a list of a position's cardinally adjacent positions
     * @param position
     * @return
     */
    public default List<Position> neighbour(Position position) {
        List<Position> neighbours = new ArrayList<>();
        neighbours.add(new Position(position.getX(), position.getY()-1));
        neighbours.add(new Position(position.getX(), position.getY()+1));
        neighbours.add(new Position(position.getX()-1, position.getY()));
        neighbours.add(new Position(position.getX()+1, position.getY()));
        
        return neighbours;
    }

    /**
     * Moves the entity
     * @param dungeon
     * @param entity
     */
    public default void move(Dungeon dungeon, Entity entity) {
        Map<Position, Position> dijkstra = dijkstra(dungeon, entity);
        Position curr = new Position(entity.getX(), entity.getY());
        Position next = dijkstra.get(curr);
        
        entity.setX(next.getX());
        entity.setY(next.getY());
    }


}
