package dungeonmania.entities.movingEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.breakableEntity.Armour;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.entities.staticEntity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.Player;

public class Mercenary extends MovingEntity implements Moveable {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // mercenary is interactable with player ( for bribing )
    private boolean isInteractable = true;

    // boolean to check if the spawned mercenary has armour
    Boolean hasArmour;

    // mercenary is spawned as hostile
    Boolean isBribed = false;

    // getting the dungeon instance
    Dungeon dungeon;

    /**
     * Constructor for the mercenary
     * @param x position
     * @param y position
     * @param dungeon 
     */
    public Mercenary(int x, int y, Dungeon dungeon) {
        super(x, y, "mercenary", 10, 1);
        setId("Mercenary" + String.valueOf(counter));
        setIsInteractable(isInteractable);
        counter++;
        this.hasArmour = Math.random() <= 0.2;
        this.dungeon = dungeon;
        this.setLayer(3);
    }

    /**
     * Method for the mercenary to move
     */
    public void move() {
        Player player = (Player) dungeon.getPlayer();
        if (player.isInvincible()) {
            if(!isBribed) {
            run(this, dungeon);
            }
        }

        List<Integer> distance = Path(this, dungeon);
        Direction move = Direction.NONE;

        int index = distance.indexOf(Collections.min(distance));
        int mindist = distance.get(index);
        List<Direction> min = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            if(distance.get(i) == mindist) {
                if (i == 0) {return;}
                if (i == 1) {min.add(Direction.UP);}
                if (i == 2) {min.add(Direction.DOWN);}
                if (i == 3) {min.add(Direction.LEFT);}
                if (i == 4) {min.add(Direction.RIGHT);}
            }
        }
       
        
        int a = 0;
        Position next = this.getPosition().translateBy(min.get(a));

        // if Position to move to is wall, do nothing;
        List<Entity> list = dungeon.getEntitiesOnSamePosition(next);
        for(Entity current : list) {
            if (current instanceof Boulder) {
                player.interactBoulder(current, next, min.get(a));
                break;
            }
            if (current instanceof Wall) {
                if(min.size() == 1) {
                    return;
                }
                a++;
                next = this.getPosition().translateBy(min.get(a));
            }

            if (current instanceof Portal) {
                Portal portal = (Portal) current;
                next = portal.correspondingPortalPosition().translateBy(min.get(a));

                List<Entity> lis = dungeon.getEntitiesOnSamePosition(next);
                for (Entity spot : lis) {
                    if(spot instanceof Wall) {
                        return;
                    }
                    if (spot instanceof Boulder) {
                        player.interactBoulder(spot, next, min.get(a));
                        break;
                    }
                }
            }
        }
        this.setX(next.getX());
        this.setY(next.getY());
    }

    /**
     * Method for the mercenary to drop armour
     */
    public void dropArmour() {
        if (!hasArmour) {
            return;
        }
        Armour armour = new Armour(this.getX(), this.getY());
        dungeon.addItem(armour);
    }

    /**
     * Method to bribe the mercenary to become an ally 
     */
    public void bribe() {
        setIsBribed(true);
        dungeon.getInventory().breakItem("treasure");
    }

    /**
     * Getter for the bribe boolean
     * @return
     */
    public Boolean IsBribed() {
        return isBribed;
    }

    /**
     * Setter for the bribe boolean
     * @param isBribed
     */
    public void setIsBribed(Boolean isBribed) {
        this.isBribed = isBribed;
    }

    public Boolean getHasArmour() {
        return hasArmour;
    }
}
