package dungeonmania.goals;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;

public class TreasureGoal extends LeafGoal {
    public TreasureGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String toString() {
        return ":treasure";
    }

    @Override
    public void update() {
        if (getEntitiesOfType("treasure").size() == 0) {
            setComplete(true);
        }      
    }
}
