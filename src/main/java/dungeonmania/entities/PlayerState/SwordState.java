package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class SwordState implements PlayerState {
    private Player player;
    private int maxDurability = 5;
    private int durability;

    public SwordState(Player player) {
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
    public Boolean isApplied() {
        return true;
    }
}
