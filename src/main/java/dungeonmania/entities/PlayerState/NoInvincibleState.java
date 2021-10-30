package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class NoInvincibleState implements PlayerState {
    private Player player;

    public NoInvincibleState(Player player) {
        this.player = player;
    }

    @Override
    public void applyEffect() {
        if (!player.getGameMode().equals("Hard")) {
            player.changeInvincibleState(new InvincibleState(player));
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
    
    public Boolean isApplied() {
        return false;
    }
}
