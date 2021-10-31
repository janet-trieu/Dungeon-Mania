package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class InvincibleState implements PlayerState{
    private Player player;
    private int duration;
    
    /**
     * Constructor for InvincibileState
     * @param player
     */
    public InvincibleState(Player player) {
        this.player = player;
        this.duration = 10;
    }

    /**
     * if gameMode is hard mode, player gains no benefits
     * if normal or peaceful, player gains additional duration and potion is consumed
     */
    @Override
    public void applyEffect() {
        if (!player.getGameMode().equals("Hard")) {
            duration += 10;
        }
        Dungeon.getDungeon().getInventory().breakItem("invincibility_potion");
    }

    /**
     * player is no longer invincible
     */
    @Override
    public void removeEffect() {
        player.changeInvincibleState(new NoInvincibleState(player));
    }

    /**
     * durability of invincibility decreases
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
