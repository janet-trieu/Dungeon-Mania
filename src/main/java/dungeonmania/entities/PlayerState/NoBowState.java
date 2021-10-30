package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoBowState implements PlayerState {
    private Player player;

    public NoBowState(Player player) {
        this.player = player;
    }

    @Override
    public void applyEffect() {
        player.changeArmourState(new BowState(player));
        player.setDamage(player.getDamage() * 2);
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
