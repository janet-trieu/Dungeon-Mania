package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.Armour;
import dungeonmania.entities.collectableEntity.buildableEntity.MidnightArmour;

public class NoMidnightArmourState implements PlayerState {

    // storing required attributes
    private Player player;

    /**
     * Constructor for NoArmourState
     * @param player
     */
    public NoMidnightArmourState(Player player) {
        this.player = player;
    }

    /**
     * player equips armour and gains benefits of increased protection
     */
    @Override
    public void applyEffect() {
        player.changeMidnightArmourState(new MidnightArmourState(player));
        // if player has armour, unequip and wear midnight instead
        if (player.isArmour()) {
            player.setProtection(player.getProtection() / Armour.protection);
        }
        player.setProtection(player.getProtection() * MidnightArmour.protection);
        player.setDamage(player.getDamage() * MidnightArmour.damage);
        
    }

    /**
     * player equips armour and gains benefits of increased protection
     */
    @Override
    public void loadDuration(int durability) {
        player.changeMidnightArmourState(new MidnightArmourState(player));
        player.setProtection(player.getProtection() * MidnightArmour.protection);
        player.setDamage(player.getDamage() * MidnightArmour.damage);
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
