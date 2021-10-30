package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoInvincibleState implements PlayerState {
    private Player player;

    public NoInvincibleState(Player player) {
        this.player = player;
    }

    @Override
    public void applyEffect() {
        if (!player.getGameMode().equals("hard")) {
            player.changeInvincibleState(new InvincibleState(player));
        }
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
    
    public Boolean isInvincible() {
        return false;
    }
}
