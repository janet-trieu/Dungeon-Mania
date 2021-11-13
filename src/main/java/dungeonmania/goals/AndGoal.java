package dungeonmania.goals;

import dungeonmania.Dungeon;

public class AndGoal extends CompositeGoal {

    /**
     * Constructor for AndGoal
     * @param dungeon
     */
    public AndGoal(Dungeon dungeon) {
        super(dungeon);
    }

    /**
     * toString method for AndGoal
     * return:
     *  - "" or
     *  - Goal.toString
     *  - (Goal.toString AND Goal.toString)
     */
    @Override
    public String toString() {
        update();
        if (getSubGoalList().size() == 0) {
            return "";
        } else if (getSubGoalList().size() == 1) {
            return getSubGoalList().get(0).toString();
        } else {
            return "(" + getSubGoalList().get(0).toString() + " AND " + getSubGoalList().get(1).toString() + ")";
        }
    }
    
    /**
     * updates all subGoals
     */
    @Override
    public void update() {
        super.update();
        if (getSubGoalList().size() == 0 || subGoalsIsComplete()) {
            setComplete(true);
        }
    }
    
}
