package dungeonmania.entities.PlayerState;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class NoInvisibleState implements PlayerState {
    private Player player;

    public NoInvisibleState(Player player) {
        this.player = player;
    }

    @Override
    public void applyEffect() {
        player.changeInvisibleState(new InvisibleState(player));
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
    
    public Boolean isApplied() {
        return false;
    }
}
