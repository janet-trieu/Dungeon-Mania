package dungeonmania.entities.PlayerState;

import dungeonmania.entities.Player;

public class NoArmourState implements PlayerState {
    private Player player;

    /**
     * Constructor for NoArmourState
     * @param player
     */
    public NoArmourState(Player player) {
        this.player = player;
    }

    /**
     * player equips armour and gains benefits of increased protection
     */
    @Override
    public void applyEffect() {
        player.changeArmourState(new ArmourState(player));
        player.setProtection(player.getProtection() * 2);
    }

    /**
     * player equips armour and gains benefits of increased protection
     */
    @Override
    public void loadDuration(int durability) {
        player.changeArmourState(new ArmourState(player, durability));
        player.setProtection(player.getProtection() * 2);
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
