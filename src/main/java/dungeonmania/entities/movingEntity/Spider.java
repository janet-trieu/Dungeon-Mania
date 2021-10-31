package dungeonmania.entities.movingEntity;
import java.util.Collections;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.util.*;

public class Spider extends MovingEntity implements Moveable {
    
    // getting the dungeon instance
    Dungeon dungeon;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // storing the list of possible position of path
    private List<Position> path;

    /**
     * Constructor for a spider
     * @param x position
     * @param y position
     * @param dungeon 
     */
    public Spider(int x, int y, Dungeon dungeon) {
        super(x, y, "spider", 1, 10);
        this.dungeon = dungeon;
        this.path = new Position(x,y).getAdjacentPositions();
        this.setLayer(3);
        setId("Spider" + String.valueOf(counter));
        counter++;
    }

    /**
     * Method to spawn the spider
     */
    public void addSpider() {
        dungeon.addEntity(this);
    }

    /**
     * Method of movement for spider entity
     */
    public void move() {
        Player player = (Player) dungeon.getPlayer();

        if (player.isInvincible()) {
            run(this, dungeon);
            this.path = new Position(this.getX(), this.getY() + 1).getAdjacentPositions();
        }

        Position next;

        // if spider is in spawn position, move it up
        if (!path.contains(this.getPosition())) {
            next = path.get(1);
            this.setPosition(next.getX(), next.getY());
            counter++;
            return;
        }

        Position now = new Position(this.getX(), this.getY());

        // getting the position the spider is to move to

        int idx = path.indexOf(now);
        if (idx == 7) {
            idx = 0;
        }
        else {
            idx++;
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
