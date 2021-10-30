package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoArmourState implements PlayerState {
    private Player player;

    public NoArmourState(Player player) {
        this.player = player;
    }

    @Override
    public void applyEffect() {
        player.changeArmourState(new ArmourState(player));
        player.setProtection(player.getProtection() * 2);
    }

    @Override
    public void removeEffect() {
        // does nothing
    }

    @Override
    public void reduceDuration() {
        // does nothing
    }

    @Override
    public Boolean isApplied() {
        return false;
    }
}
