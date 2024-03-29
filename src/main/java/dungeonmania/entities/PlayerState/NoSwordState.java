package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.Sword;

public class NoSwordState implements PlayerState {

    // storing required attributes
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
        player.setDamage(player.getDamage() * Sword.damage);
    }

    /**
     * player equips sword and gains benefit of increased damage
     */
    @Override
    public void loadDuration(int duration) {
        player.changeSwordState(new SwordState(player, duration));
        player.setDamage(player.getDamage() * Sword.damage);
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
