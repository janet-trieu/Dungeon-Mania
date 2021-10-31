package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class InvisibleState implements PlayerState{
    private Player player;
    private int duration;

    /**
     * Constructor for InvisibleState
     * @param player
     */
    public InvisibleState(Player player) {
        this.player = player;
        this.duration = 10;
    }

    /**
     * Constructor for InvisibleState
     * @param player
     * @param duration
     */
    public InvisibleState(Player player, int duration) {
        this.player = player;
        this.duration = duration;
    }

    /**
     * Adds additional duration to player and consumes potion
     */
    @Override
    public void applyEffect() {
        duration += 10;
        Dungeon.getDungeon().getInventory().breakItem("invisibility_potion");
    }

    /**
     * player is no longer invisible
     */
    @Override
    public void removeEffect() {
        player.changeInvisibleState(new NoInvisibleState(player));
    }

    /**
     * duration reduces
     */
    @Override
    public void reduceDuration() {
        duration--;
        if (duration <= 0) {
            removeEffect();
        }
    }
    
    public Boolean isApplied() {
        return true;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
