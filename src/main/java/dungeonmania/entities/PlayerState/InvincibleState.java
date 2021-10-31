package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class InvincibleState implements PlayerState{
    private Player player;
    private int duration;
    
    public InvincibleState(Player player) {
        this.player = player;
        this.duration = 10;
    }

    @Override
    public void applyEffect() {
        if (!player.getGameMode().equals("Hard")) {
            duration += 10;
        }
        Dungeon.getDungeon().getInventory().breakItem("invincibility_potion");
    }

    @Override
    public void removeEffect() {
        player.changeInvincibleState(new NoInvincibleState(player));
    }

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
