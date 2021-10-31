package dungeonmania.entities.movingEntity;

import java.util.Collections;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.breakableEntity.Armour;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.Player;
public class Mercenary extends MovingEntity implements Moveable {
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
    private boolean isInteractable = true;
    Boolean hasArmour;
    Boolean isBribed = false;
    Dungeon dungeon;

    public Mercenary(int x, int y, Dungeon dungeon) {
        super(x, y, "mercenary", 10, 1);
        setId("Mercenary" + String.valueOf(counter));
        setIsInteractable(isInteractable);
        counter++;
        this.hasArmour = Math.random() <= 0.2;
        this.dungeon = dungeon;
        this.setLayer(3);
    }

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

        Position next = this.getPosition().translateBy(move);
        //if Position to move to is wall, do nothing;
        List<Entity> list = dungeon.getEntitiesOnSamePosition(next);
        for(Entity current : list) {
            if (current instanceof Boulder) {
                player.interactBoulder(current, next, move);
            }
            else if (current instanceof Wall) {
                return;
            }
        }
        this.setX(next.getX());
        this.setY(next.getY());
    }

    


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

    public Boolean IsBribed() {
        return isBribed;
    }

    public void setIsBribed(Boolean isBribed) {
        this.isBribed = isBribed;
    }

}
