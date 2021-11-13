package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class AndurilState implements PlayerState {

    // storing required attributes
    private Player player;

    /**
     * Constructor for ArmourState
     * @param player
     */
    public AndurilState(Player player) {
        this.player = player;
    }

    /**
     * Cannot apply another Anduril if player has already equipped Anduril
     * Player can only wear one Anduril at a time and multiple Anduril does
     * not stack
     */
    @Override
    public void applyEffect() {
        // does nothing
    }

    /**
     * Anduril cannot break
     */
    @Override
    public void removeEffect() {
    }

    /**
     * Anduril does not reduce in durability
     */
    @Override
    public void reduceDuration() {
    }

    @Override
    public int getDuration() {
        return 100;
    }

    /**
     * check if player is equipped Anduril
     */
    @Override
    public Boolean isApplied() {
        return true;
    }

    @Override
    public void loadDuration(int duration) {
        //does nothing      
    }
    
}
