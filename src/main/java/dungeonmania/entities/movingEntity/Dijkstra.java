package dungeonmania.entities.movingEntity;

import java.util.HashMap;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public interface Dijkstra {
    
public default HashMap<Position, Integer> graph(Dungeon dungeon, Entity entity) {
    HashMap<Position, Integer> graph = new HashMap<>();
    Player player = (Player) dungeon.getPlayer();
    Position posP = new Position(player.getX(), player.getY());
    Position posE = new Position(entity.getX(), entity.getY());
    if(posP.getX() > posE.getX()) {

    }




    return graph;

}






}
