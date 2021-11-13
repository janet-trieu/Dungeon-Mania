package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class AndurilState implements PlayerState {

    // storing required attributes
    private Player player;
    private int maxDurability = 10;
    private int durability;

    /**
     * Constructor for ArmourState
     * @param player
     */
    public AndurilState(Player player) {
        this.player = player;
        durability = maxDurability;
    }

    /**
     * 
     * @param player
     * @param durability
     */
    public AndurilState(Player player, int durability) {
        this.player = player;
        this.durability = durability;
    }

    /**
     * Cannot apply another Anduril if player has already equipped Anduril
     * Player can only wear one Anduril at a time and multiple Anduril does
     * not stack
     */
    @Override
    public void applyEffect() {
        // does nothing
    }

    /**
     * Anduril breaks and is removed from inventory and no longer gains benefits from armour
     */
    @Override
    public void removeEffect() {
        player.setDamage(player.getDamage() / 2);
        if (player.isSword()) {
            player.setDamage(player.getDamage() * 2);
        }
        player.changeAndurilState(new NoAndurilState(player));
        Dungeon.getDungeon().getInventory().breakItem("anduril");
    }

    /**
     * Anduril reduces in durability
     */
    @Override
    public void reduceDuration() {
        durability--;
        if (durability <= 0) {
            removeEffect();
        }
    }

    @Override
    public int getDuration() {
        return this.durability;
    }

    /**
     * check if player is equipped Anduril
     */
    @Override
    public Boolean isApplied() {
        return true;
    }

    @Override
    public void loadDuration(int duration) {
        //does nothing      
    }
    
}
