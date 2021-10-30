package dungeonmania.entities.movingEntity;
import java.util.Collections;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.util.*;

public class Spider extends MovingEntity {
    
    Dungeon dungeon;
    private static int counter = 0;
    private List<Position> path;

    public Spider(int x, int y, Dungeon dungeon) {
        super(x, y, "spider", 1, 10);
        this.dungeon = dungeon;
        this.path = new Position(x,y).getAdjacentPositions();
        
        setId("Spider" + String.valueOf(counter));
        counter++;
    }

    public void addSpider() {
        dungeon.addEntity(this);
    }

    public void move() {
        Position next;
        //if spider is in spawn position, move it up
        if (counter == 0) {
            next = path.get(1);
            this.setPosition(next.getX(), next.getY());
            counter++;
            return;
        }
        //getting the position the spider is to move to
        int idx = 0;
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i) == this.getPosition()) {
                idx = i++;
                break;
            }
        }
        //check if boulder exists in the position spider is to be moved to
        next = path.get(idx);
        List<Entity> list = dungeon.getEntitiesOnSamePosition(next);
        for(Entity current : list) {
            if (current.getType() == "boulder") {
                //reverse the path the spider takes
                Collections.reverse(this.path);
                move();
                return;
            }
        }
        //Set the spiders position
        this.setPosition(next.getX(), next.getY());
    }
        
}
