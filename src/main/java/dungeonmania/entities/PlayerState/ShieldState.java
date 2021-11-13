package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.buildableEntity.Shield;

public class ShieldState implements PlayerState {

    // storing required attributes
    private Player player;
    private int durability;

    /**
     * Constructor for ShieldState
     * @param player
     */
    public ShieldState(Player player) {
        this.player = player;
        durability = Shield.maxDurability;
    }

    /**
     * Constructor for ShieldState
     * @param player
     * @param durability
     */
    public ShieldState(Player player, int durability) {
        this.player = player;
        this.durability = durability;
    }

    @Override
    public void applyEffect() {
        // does nothing
    }

    /**
     * player unequips shield and shield is destroyed
     * player no longer gains benefit from shield
     */
    @Override
    public void removeEffect() {
        player.setProtection(player.getProtection() / Shield.protection);
        player.changeShieldState(new NoShieldState(player));
        Dungeon.getDungeon().getInventory().breakItem("shield");
    }

    @Override
    public void reduceDuration() {
        durability--;
        if (durability <= 0) {
            removeEffect();
        }
    }

    @Override
    public void loadDuration(int duration) {
        //does nothing      
    }
    
    @Override
    public int getDuration() {
        return this.durability;
    }

    @Override
    public Boolean isApplied() {
        return true;
    }

}
