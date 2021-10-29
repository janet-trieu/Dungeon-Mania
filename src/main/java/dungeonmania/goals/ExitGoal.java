package dungeonmania.goals;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;

public class ExitGoal extends LeafGoal {
    public ExitGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String toString() {
        if (isComplete()) {
            return "";
        } else {
            return ":exit";
        }
    }

    @Override
    public void update() {
        List<Entity> exitList = getEntitiesOfType("exit");
        Boolean playerAtExit = false;
        for (Entity exit : exitList) {
            // if player is at same position as an exit, goal is true
            if (dungeon.getInfo("player").getPosition().equals(exit.getPosition())) {
                setComplete(true);
                playerAtExit = true;
            }
        }
        // if player is not at an exit, goal is false
        if (playerAtExit == false) {
            setComplete(false);
        }
    }
}
