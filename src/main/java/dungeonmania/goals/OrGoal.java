package dungeonmania.goals;

import dungeonmania.Dungeon;


public class OrGoal extends CompositeGoal {

    public OrGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String toString() {
        if (getSubGoalList().size() == 1) {
            return getSubGoalList().get(0).toString();
        } else {
            return "(" + getSubGoalList().get(0).toString() + " OR " + getSubGoalList().get(1).toString() + ")";
        }
    }

    @Override
    public void update() {
        super.update();
        if (getSubGoalList().size() <= 1) {
            setComplete(true);
        }
    }
}
