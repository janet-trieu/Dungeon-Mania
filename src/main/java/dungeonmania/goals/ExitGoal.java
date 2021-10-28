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
        return ":exit";
    }

    @Override
    public void update() {
        List<Entity> exitList = getEntitiesOfType("exit");
        Boolean playerAtExit = false;
        for (int i = 0; i < exitList.size(); i++) {
            // if player is at same position as an exit, goal is true
            if (dungeon.getPlayer().getPosition().equals(exitList.get(i).getPosition())) {
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
