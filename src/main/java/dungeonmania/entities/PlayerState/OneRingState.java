package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class OneRingState implements PlayerState {
    private Player player;

    /**
     * Constructor for OneRingState
     * @param player
     */
    public OneRingState(Player player) {
        this.player = player;
    }

    @Override
    public void applyEffect() {
        // does nothing
    }

    /**
     * Player is healed to full health and OneRing is destroyed and unequipped
     */
    @Override
    public void removeEffect() {
        player.healToFullHealth();
        player.changeOneRingState(new NoOneRingState(player));
        Dungeon.getDungeon().getInventory().breakItem("the_one_ring");
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
        return true;
    }
}
