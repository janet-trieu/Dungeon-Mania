package dungeonmania.goals;

import dungeonmania.Dungeon;

public class AndGoal extends CompositeGoal {

    public AndGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String toString() {
        return "(" + getSubGoalList().get(0).toString() + " AND " + getSubGoalList().get(1).toString() + ")";
    }
    @Override
    public void update() {
        super.update();
        if (getSubGoalList().size() == 0) {
            setComplete(true);
        }
    }
}
