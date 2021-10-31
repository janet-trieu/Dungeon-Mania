package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class NoInvincibleState implements PlayerState {
    private Player player;

    /**
     * Constructor for NoInvincibileState
     * @param player
     */
    public NoInvincibleState(Player player) {
        this.player = player;
    }

    /**
     * if the gameMode is hard, player does not gain invincibility
     * consume invincibility potion
     */
    @Override
    public void applyEffect() {
        if (!player.getGameMode().equals("Hard")) {
            player.changeInvincibleState(new InvincibleState(player));
        }
        Dungeon.getDungeon().getInventory().breakItem("invincibility_potion");
    }

    /**
     * if the gameMode is hard, player does not gain invincibility
     * consume invincibility potion
     */
    @Override
    public void loadDuration(int duration) {
        if (!player.getGameMode().equals("Hard")) {
            player.changeInvincibleState(new InvincibleState(player, duration));
        }
        Dungeon.getDungeon().getInventory().breakItem("invincibility_potion");
    }

    @Override
    public void removeEffect() {
        // Does nothing
        return;
    }

    @Override
    public void reduceDuration() {
        // Does nothing
        return;        
    }
    
    @Override
    public int getDuration() {
        return 0;
    }

    public Boolean isApplied() {
        return false;
    }
}
