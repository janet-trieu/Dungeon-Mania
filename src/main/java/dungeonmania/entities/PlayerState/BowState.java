package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class BowState implements PlayerState {
    private Player player;
    private int maxDurability = 3;
    private int durability;

    public BowState(Player player) {
        this.player = player;
        durability = maxDurability;
    }

    @Override
    public void applyEffect() {
        // does nothing
    }

    @Override
    public void removeEffect() {
        player.setDamage(player.getDamage() / 2);
        player.changeBowState(new NoBowState(player));
        Dungeon.getDungeon().getInventory().breakItem("bow");
    }

    @Override
    public void reduceDuration() {
        durability--;
        if (durability <= 0) {
            removeEffect();
        }
        // break Bow
    }

    @Override
    public Boolean isApplied() {
        return true;
    }
}
