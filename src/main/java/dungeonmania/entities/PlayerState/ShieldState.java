package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class ShieldState implements PlayerState {
    private Player player;
    private int maxDurability = 5;
    private int durability;

    public ShieldState(Player player) {
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
    public Boolean isApplied() {
        return true;
    }
}
