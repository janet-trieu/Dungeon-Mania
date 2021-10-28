package dungeonmania.entities.PotionState;

import dungeonmania.entities.Player;

public class NoInvincibleState implements PotionState {
    private Player player;

    public NoInvincibleState(Player player) {
        this.player = player;
    }

    @Override
    public void applyEffect() {
        player.changeInvincibleState(new InvincibleState(player));
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
    
}
