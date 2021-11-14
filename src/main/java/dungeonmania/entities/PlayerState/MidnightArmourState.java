package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class MidnightArmourState implements PlayerState {

    // storing required attributes
    private Player player;

    /**
     * Constructor for ArmourState
     * @param player
     */
    public MidnightArmourState(Player player) {
        this.player = player;
    }

    /**
     * Cannot apply another armour if player has already equipped armour
     * Player can only wear one armour at a time and multiple armour does
     * not stack
     */
    @Override
    public void applyEffect() {
        // does nothing
    }

    /**
     * Midnight Armour cannot break
     */
    @Override
    public void removeEffect() {
    }

    /**
     * Midnight Armour does not reduce in durability
     */
    @Override
    public void reduceDuration() {
    }

    @Override
    public int getDuration() {
        return 100;
    }

    /**
     * check if player is equipped armour
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
