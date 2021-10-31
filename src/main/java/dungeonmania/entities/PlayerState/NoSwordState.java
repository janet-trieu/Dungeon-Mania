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
