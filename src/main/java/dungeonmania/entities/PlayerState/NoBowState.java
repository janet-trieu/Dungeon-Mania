package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoBowState implements PlayerState {
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
        player.setDamage(player.getDamage() * 2);
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
    public Boolean isApplied() {
        return false;
    }
}
