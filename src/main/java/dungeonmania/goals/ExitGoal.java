package dungeonmania.goals;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;

public class ExitGoal extends LeafGoal {

    /**
     * constructor for ExitGoal
     * @param dungeon
     */
    public ExitGoal(Dungeon dungeon) {
        super(dungeon);
    }

    /**
     * toString method
     * return: ":exit" or ""
     */
    @Override
    public String toString() {
        if (isComplete()) {
            return "";
        } else {
            return ":exit";
        }
    }

    /**
     * checks if player is currently at exit
     * if at exit, goal is complete
     * else, goal is incomplete
     */
    @Override
    public void update() {
        List<Entity> exitList = getEntitiesOfType("exit");
        Boolean playerAtExit = false;
        for (Entity exit : exitList) {
            // if player is at same position as an exit, goal is true
            if (getDungeon().getPlayer() != null && 
                getDungeon().getPlayer().getPosition().equals(exit.getPosition())) {
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
