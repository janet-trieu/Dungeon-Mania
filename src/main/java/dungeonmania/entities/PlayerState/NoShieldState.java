package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoShieldState implements PlayerState {
    private Player player;

    /**
     * Constructor for NoShieldState
     * @param player
     */
    public NoShieldState(Player player) {
        this.player = player;
    }

    /**
     * player equips shield and gains benefit of increased protection
     */
    @Override
    public void applyEffect() {
        player.changeShieldState(new ShieldState(player));
        player.setProtection(player.getProtection() * 2);
    }

    /**
     * player equips shield and gains benefit of increased protection
     */
    @Override
    public void loadDuration(int duration) {
        player.changeShieldState(new ShieldState(player, duration));
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
    public int getDuration() {
        return 0;
    }
    @Override
    public Boolean isApplied() {
        return false;
    }
}
