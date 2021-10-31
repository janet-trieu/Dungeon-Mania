package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoOneRingState implements PlayerState {
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
    public Boolean isApplied() {
        return false;
    }
}
