package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class MidnightArmourState implements PlayerState {

    // storing required attributes
    private Player player;

    /**
     * Constructor for ArmourState
     * @param player
     */
    public MidnightArmourState(Player player) {
        this.player = player;
    }

    // /**
    //  * 
    //  * @param player
    //  * @param durability
    //  */
    // public MidnightArmourState(Player player, int durability) {
    //     this.player = player;
    // }

    /**
     * Cannot apply another armour if player has already equipped armour
     * Player can only wear one armour at a time and multiple armour does
     * not stack
     */
    @Override
    public void applyEffect() {
        // does nothing
    }

    // /**
    //  * Armour breaks and is removed from inventory and no longer gains benefits from armour
    //  */
    // @Override
    // public void removeEffect() {
    //     // remove stats from midnight armour
    //     player.setProtection(player.getProtection() / MidnightArmour.protection);
    //     player.setDamage(player.getDamage() / MidnightArmour.damage);
    //     // reapply stats of armour if applicable
    //     if (player.isArmour()) {
    //         player.setProtection(player.getProtection() * Armour.protection);
    //     }
    //     player.changeArmourState(new NoArmourState(player));
    //     Dungeon.getDungeon().getInventory().breakItem("midnight_armour");
    // }

    // /**
    //  * armour reduces in durability
    //  */
    // @Override
    // public void reduceDuration() {
    //     durability--;
    //     if (durability <= 0) {
    //         removeEffect();
    //     }
    // }

    // @Override
    // public int getDuration() {
    //     return this.durability;
    // }

    /**
     * Midnight Armour cannot break
     */
    @Override
    public void removeEffect() {
    }

    /**
     * Midnight Armour does not reduce in durability
     */
    @Override
    public void reduceDuration() {
    }

    @Override
    public int getDuration() {
        return 100;
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
