package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class BowState implements PlayerState {

    // storing required attributes
    private Player player;
    private int maxDurability = 3;
    private int durability;

    /**
     * Constructor for BowState
     * @param player
     */
    public BowState(Player player) {
        this.player = player;
        durability = maxDurability;
    }

    /**
     * Constructor for BowState
     * @param player
     * @param durability
     */
    public BowState(Player player, int durability) {
        this.player = player;
        this.durability = durability;
    }

    /**
     * Cannot apply another bow if player has already equipped bow
     * Player can only wear one bow at a time and multiple bows does
     * not stack
     */
    @Override
    public void applyEffect() {
        // does nothing
    }

    /**
     * bow breaks and is removed from inventory and no longer gains benefits from bow
     */
    @Override
    public void removeEffect() {
        player.setDamage(player.getDamage() / 2);
        player.changeBowState(new NoBowState(player));
        Dungeon.getDungeon().getInventory().breakItem("bow");
    }

    /**
     * bow reduces in durability
     */
    @Override
    public void reduceDuration() {
        durability--;
        if (durability <= 0) {
            removeEffect();
        }
        // break Bow
    }

    @Override
    public int getDuration() {
        return this.durability;
    }
    
    @Override
    public Boolean isApplied() {
        return true;
    }
    
    @Override
    public void loadDuration(int duration) {
        //does nothing      
    }
    
}
