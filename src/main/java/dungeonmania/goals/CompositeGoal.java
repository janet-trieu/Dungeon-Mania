package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import dungeonmania.Dungeon;

public abstract class CompositeGoal extends Goal {
    private List<Goal> subGoalList = new ArrayList<Goal>();
    
    public CompositeGoal(Dungeon dungeon) {
        super(dungeon);
    }

    public List<Goal> getSubGoalList() {
        return subGoalList;
    }

    public void addSubGoal(Goal goal) {
        subGoalList.add(goal);
    }

    public void removeSubGoal(Goal goal) {
        subGoalList.remove(goal);
    }
    
    @Override
    public Boolean isLeaf() {
        return false;
    }

    @Override
    public void update() {
        ListIterator<Goal> iter = subGoalList.listIterator();
        while(iter.hasNext()) {
            // updates the child goals
            Goal current = iter.next();
            current.update();
            // if the goal is exit but is not the only goal, ignore
            if (current instanceof ExitGoal && subGoalList.size() > 1) {
            // if goal is complete, remove from goal list
            } else if (current.isComplete()) {
                iter.remove();
            }
        }
    } 
}
