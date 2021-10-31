package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoOneRingState implements PlayerState {

    // storing required attributes
    private Player player;

    /**
     * Constructor for NoRingState
     * @param player
     */
    public NoOneRingState(Player player) {
        this.player = player;
    }

    /**
     * Player equips OneRing
     */
    @Override
    public void applyEffect() {
        player.changeOneRingState(new OneRingState(player));

    }

    @Override
    public void removeEffect() {
        // does nothing
    }

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

    @Override
    public void loadDuration(int duration) {
        // does nothing
    }
    
}
