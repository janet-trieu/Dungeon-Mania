package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.Sword;
import dungeonmania.entities.collectableEntity.rareCollectableEntity.Anduril;

public class NoAndurilState implements PlayerState {

    // storing required attributes
    private Player player;

    /**
     * Constructor for NoArmourState
     * @param player
     */
    public NoAndurilState(Player player) {
        this.player = player;
    }

    /**
     * player equips armour and gains benefits of increased protection
     */
    @Override
    public void applyEffect() {
        player.changeAndurilState(new AndurilState(player));
        if (player.isSword()) {
            player.setDamage(player.getDamage() / Sword.damage);
        }
        player.setDamage(player.getDamage() * Anduril.damage);
    }

    /**
     * Does not get loaded in as persistence does not matter for Anduril
     */
    @Override
    public void loadDuration(int durability) {

    }

    /**
     * player unequips nothing
     * nothing happens
     */
    @Override
    public void removeEffect() {
    }

    /**
     * non existent armour duration decreases
     * nothing happens
     */
    @Override
    public void reduceDuration() {
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

