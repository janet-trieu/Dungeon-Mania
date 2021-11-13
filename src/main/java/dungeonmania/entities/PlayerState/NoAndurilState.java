package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

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
            player.setDamage(player.getDamage() / 2);
        }
        player.setDamage(player.getDamage() * 2);
    }

    /**
     * player equips armour and gains benefits of increased protection
     */
    @Override
    public void loadDuration(int durability) {
        player.changeAndurilState(new AndurilState(player, durability));
        player.setDamage(player.getProtection() * 2);
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

