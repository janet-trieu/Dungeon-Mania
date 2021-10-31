package dungeonmania.goals;

import dungeonmania.Dungeon;


public class OrGoal extends CompositeGoal {

    /**
     * Constructor for OrGoal
     * @param dungeon
     */
    public OrGoal(Dungeon dungeon) {
        super(dungeon);
    }

    /**
     * toString method for OrGoal
     * return:
     *  - "" or
     *  - Goal.toString
     *  - (Goal.toString OR Goal.toString)
     */
    @Override
    public String toString() {
        if (isComplete()){
            return "";
        } if (getSubGoalList().size() == 1) {
            return getSubGoalList().get(0).toString();
        } else {
            return "(" + getSubGoalList().get(0).toString() + " OR " + getSubGoalList().get(1).toString() + ")";
        }
    }
    
    /**
     * updates all subGoals
     */
    @Override
    public void update() {
        super.update();
        if (getSubGoalList().size() <= 1 || subGoalsIsComplete()) {
            setComplete(true);
        }
    }
}
