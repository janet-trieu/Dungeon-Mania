package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.breakableEntity.Sword;

public class SwordState implements PlayerState {

    // storing required attributes
    private Player player;
    private int durability;

    /**
     * Constructor for SwordState
     * @param player
     */
    public SwordState(Player player) {
        this.player = player;
        durability = Sword.durability;
    }

    /**
     * Constructor for SwordState
     * @param player
     */
    public SwordState(Player player, int durability) {
        this.player = player;
        this.durability = durability;
    }

    @Override
    public void applyEffect() {
        // does nothing
    }

    /**
     * player unequips sword and sword is destroyed
     * player no longer gains benefits from sword
     */
    @Override
    public void removeEffect() {
        player.setDamage(player.getDamage() / Sword.damage);
        player.changeSwordState(new NoSwordState(player));
        Dungeon.getDungeon().getInventory().breakItem("sword");
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
