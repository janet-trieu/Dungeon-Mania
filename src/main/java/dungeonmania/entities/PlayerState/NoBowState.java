package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.buildableEntity.Bow;

public class NoBowState implements PlayerState {

    // storing required attributes
    private Player player;

    /**
     * Constructor for NoBowState
     * @param player
     */
    public NoBowState(Player player) {
        this.player = player;
    }

    /**
     * player equips bow and 
     */
    @Override
    public void applyEffect() {
        player.changeBowState(new BowState(player));
        player.setDamage(player.getDamage() * Bow.damage);
    }

    /**
     * player equips bow and 
     */
    @Override
    public void loadDuration(int durability) {
        player.changeBowState(new BowState(player, durability));
        player.setDamage(player.getDamage() * Bow.damage);
    }

    /**
     * player unequips nothing
     * nothing happens
     */
    @Override
    public void removeEffect() {
    }

    /**
     * non existent bow duration decrease
     * nothing happens
     */
    @Override
    public void reduceDuration() {
        // does nothing
    }
    
    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public Boolean isApplied() {
        return false;
    }

}
