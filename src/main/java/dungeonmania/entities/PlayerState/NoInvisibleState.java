package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoInvisibleState implements PlayerState {
    private Player player;

    public NoInvisibleState(Player player) {
        this.player = player;
    }

    @Override
    public void applyEffect() {
        player.changeInvisibleState(new InvisibleState(player));
        
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
    
    public Boolean isInvisible() {
        return false;
    }
}
