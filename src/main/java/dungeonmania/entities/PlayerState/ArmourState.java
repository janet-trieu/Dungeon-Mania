package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class ArmourState implements PlayerState {
    private Player player;
    private int maxDurability = 8;
    private int durability;

    public ArmourState(Player player) {
        this.player = player;
        durability = maxDurability;
    }

    @Override
    public void applyEffect() {
        // does nothing
    }

    @Override
    public void removeEffect() {
        player.setProtection(player.getProtection() / 2);
        player.changeArmourState(new NoArmourState(player));
        Dungeon.getDungeon().getInventory().breakItem("armour");
    }

    @Override
    public void reduceDuration() {
        durability--;
        if (durability <= 0) {
            removeEffect();
        }
    }

    @Override
    public Boolean isApplied() {
        return true;
    }
    
}
