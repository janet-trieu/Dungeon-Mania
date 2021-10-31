package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class NoInvisibleState implements PlayerState {
    private Player player;

    /**
     * Constructor for NoInvisibileState
     * @param player
     */
    public NoInvisibleState(Player player) {
        this.player = player;
    }
    
    /**
     * Player gains invisibility
     * consumes invisibility potion
     */
    @Override
    public void applyEffect() {
        player.changeInvisibleState(new InvisibleState(player));
        Dungeon.getDungeon().getInventory().breakItem("invisibility_potion");
    }

    /**
     * Player gains invisibility
     * consumes invisibility potion
     */
    @Override
    public void loadDuration(int duration) {
        player.changeInvisibleState(new InvisibleState(player, duration));
        Dungeon.getDungeon().getInventory().breakItem("invisibility_potion");
    }
    @Override
    public void removeEffect() {
        // Does nothing
        return;        
    }

    @Override
    public void reduceDuration() {
        // Does nothing
        return;        
    }
    
    @Override
    public int getDuration() {
        return 0;
    }

    public Boolean isApplied() {
        return false;
    }
}
