package dungeonmania.entities.PotionState;

import dungeonmania.entities.Player;

public class InvincibleState implements PotionState{
    private Player player;
    private int duration;
    
    public InvincibleState(Player player) {
        this.player = player;
        this.duration = 5;
    }

    @Override
    public void applyEffect() {
        duration += 5;
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

    public Boolean isInvincible() {
        return true;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
