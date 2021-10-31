package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoSwordState implements PlayerState {
    private Player player;

    /**
     * Constructor for NoSwordState
     * @param player
     */
    public NoSwordState(Player player) {
        this.player = player;
    }

    /**
     * player equips sword and gains benefit of increased damage
     */
    @Override
    public void applyEffect() {
        player.changeSwordState(new SwordState(player));
        player.setDamage(player.getDamage() * 2);
    }

    /**
     * player equips sword and gains benefit of increased damage
     */
    @Override
    public void loadDuration(int duration) {
        player.changeSwordState(new SwordState(player, duration));
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
    public int getDuration() {
        return 0;
    }
    @Override
    public Boolean isApplied() {
        return false;
    }
}
