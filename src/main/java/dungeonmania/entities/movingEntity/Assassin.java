package dungeonmania.entities.movingEntity;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity.Sceptre;
import dungeonmania.exceptions.InvalidActionException;

public class Assassin extends BossEntity implements Bribeable {
    
    private static int counter = 0;

    // assassin is interactable with player ( for bribing )
    private boolean isInteractable = true;

    // assassin is spawned as hostile
    Boolean isBribed = false;

    // dungeon instance
    Dungeon dungeon;

    public Assassin(int x, int y, Dungeon dungeon) {
        super(x, y, "assassin", 30, 5); 
        setId("Assassin" + String.valueOf(counter));
        setIsInteractable(isInteractable);
        counter++;
    }

    /**
     * Method to bribe the assassin with the one ring and treasure to become an ally 
     */
    @Override
    public void bribe() {
        Inventory inventory = dungeon.getInventory();
        if (inventory.numberOfItem("sceptre") > 0) {
            CollectableEntity item = inventory.invGetInstance("sceptre");
            Sceptre sceptre = (Sceptre)item;
            sceptre.mindControl(this);
            return;
        }
        // if player has sun stone and the one ring, use to bribe assassin
        // one ring is used up, sun stone remains
        if (inventory.numberOfItem("sun_stone") > 0 && inventory.numberOfItem("one_ring") > 0) {
            setIsBribed(true);
            inventory.breakItem("one_ring");
            return;
            
        // else, player uses treasure and the one ring to bribe assassin    
        } else if (inventory.numberOfItem("treasure") > 0 && inventory.numberOfItem("one_ring") > 0) {
            setIsBribed(true);
            inventory.breakItem("treasure");
            inventory.breakItem("one_ring");
            return;

        // player cannot bribe assassin    
        } else {
            throw new InvalidActionException("Cannot bribe without treasure or sun stone and one ring");
        }
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isBribed() {
        return isBribed;
    }

    @Override
    public void setIsBribed(boolean isBribed) {
        this.isBribed = isBribed;
    }
    
}
