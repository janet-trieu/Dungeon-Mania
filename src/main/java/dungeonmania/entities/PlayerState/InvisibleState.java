package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class InvisibleState implements PlayerState{
    private Player player;
    private int duration;

    public InvisibleState(Player player) {
        this.player = player;
        this.duration = 5;
    }

    @Override
    public void applyEffect() {
        duration += 5;
    }

    @Override
    public void removeEffect() {
        player.changeInvisibleState(new NoInvisibleState(player));
    }

    @Override
    public void reduceDuration() {
        duration--;
        if (duration <= 0) {
            removeEffect();
        }
    }
    
    public Boolean isInvisible() {
        return true;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
