package dungeonmania.entities.movingEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.Inventory;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.entities.staticEntity.SwampTile;
import dungeonmania.entities.staticEntity.Wall;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends BossEntity implements Bribeable, Dijkstra {
    
    private static int counter = 0;

    // assassin is interactable with player ( for bribing )
    private boolean isInteractable = true;

    //
    private int mindControlDuration = 10;

    // assassin is spawned as hostile
    private boolean isBribed = false;

    // dungeon instance
    Dungeon dungeon;

    public Assassin(int x, int y, Dungeon dungeon) {
        super(x, y, "assassin", 30, 5); 
        setId("Assassin" + String.valueOf(counter));
        setIsInteractable(isInteractable);
        this.dungeon = dungeon;
        counter++;
        super.setDamagepeaceful(dungeon);
    }

    @Override
    public void takeDamagePlayer(Player player) {
        if (player.isAnduril()) {
            super.takeDamage(player.getHealth(), player.getDamage() * 3);
        } else {
            super.takeDamage(player.getHealth(), player.getDamage());
        }
    }

    public static void setCounter(int counter) {
        Assassin.counter = counter;
    }

    /**
     * Method to bribe the assassin with the one ring and treasure to become an ally 
     */
    @Override
    public void bribe() {
        Inventory inventory = dungeon.getInventory();
        if (isBribed()) {
            return;
        }

        // if player has sceptre, used as priority to mind control assassin
        if (inventory.numberOfItem("sceptre") > 0) {
            setMindControlDuration(10);
            setIsBribed(true);
            setIsInteractable(false);
            return;
        }
        
        // if player has sun stone and the one ring, use to bribe assassin
        // one ring is used up, sun stone remains
        if (inventory.numberOfItem("sun_stone") > 0 && inventory.numberOfItem("the_one_ring") > 0) {
            setIsBribed(true);
            inventory.breakItem("one_ring");
            setIsInteractable(false);
            return;

        // else, player uses treasure and the one ring to bribe assassin    
        } else if (inventory.numberOfItem("treasure") > 0 && inventory.numberOfItem("the_one_ring") > 0) {
            setIsBribed(true);
            inventory.breakItem("treasure");
            inventory.breakItem("one_ring");
            setIsInteractable(false);
            return;

        // player cannot bribe assassin    
        } else {
            throw new InvalidActionException("Cannot bribe without treasure or sun stone and one ring");
        }
    }

    @Override
    public void updateMindControl() {
        if (getMindControlDuration() == 0) {
            setIsInteractable(true);
            setIsBribed(false);
        }
    }

    @Override
    public boolean isMindControlled() {
        if (getMindControlDuration() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Method for assassin to move
     */
    @Override
    public void move() {
        if(this.getDebuff() > 0) {
            setDebuff(this.getDebuff() - 1);
            return;
        }

        Player player = (Player) dungeon.getPlayer();
        if (player != null && player.isInvincible()) {
            if(!isBribed) {
            run(this, dungeon);
            }
        }
        move(dungeon, this);
        
        List<Entity> list = dungeon.getEntitiesOnSamePosition(getPosition());
        for(Entity current : list) {
            if (current instanceof SwampTile) {
                SwampTile tile = (SwampTile) current;
                setDebuff(tile.getMovementFactor()- 1);
            }
        }
    }
    @Override
    public boolean isBribed() {
        return isBribed;
    }

    @Override
    public void setIsBribed(boolean isBribed) {
        this.isBribed = isBribed;
    }
    
    @Override
    public int getMindControlDuration() {
        return mindControlDuration;
    }

    @Override
    public void setMindControlDuration(int mindControlDuration) {
        this.mindControlDuration = mindControlDuration;
    }

}
