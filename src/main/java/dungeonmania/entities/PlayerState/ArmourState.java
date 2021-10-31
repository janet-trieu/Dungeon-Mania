package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class ArmourState implements PlayerState {
    private Player player;
    private int maxDurability = 8;
    private int durability;

    /**
     * Constructor for ArmourState
     * @param player
     */
    public ArmourState(Player player) {
        this.player = player;
        durability = maxDurability;
    }
    /**
     * 
     * @param player
     * @param durability
     */
    public ArmourState(Player player, int durability) {
        this.player = player;
        this.durability = durability;
    }


    /**
     * Cannot apply another armour if player has already equipped armour
     * Player can only wear one armour at a time and multiple armour does
     * not stack
     */
    @Override
    public void applyEffect() {
        // does nothing
    }

    /**
     * Armour breaks and is removed from inventory and no longer gains benefits from armour
     */
    @Override
    public void removeEffect() {
        player.setProtection(player.getProtection() / 2);
        player.changeArmourState(new NoArmourState(player));
        Dungeon.getDungeon().getInventory().breakItem("armour");
    }

    /**
     * armour reduces in durability
     */
    @Override
    public void reduceDuration() {
        durability--;
        if (durability <= 0) {
            removeEffect();
        }
    }

    /**
     * check if player is equipped armour
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
