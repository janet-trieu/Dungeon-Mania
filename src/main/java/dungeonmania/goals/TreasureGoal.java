package dungeonmania.goals;


import dungeonmania.Dungeon;

public class TreasureGoal extends LeafGoal {
    /**
     * Constructor for TreasureGoal
     * @param dungeon
     */
    public TreasureGoal(Dungeon dungeon) {
        super(dungeon);
    }

    /**
     * toString method
     * return: 
     *  - ":treasure" or
     *  - ""
     */
    @Override
    public String toString() {
        if (isComplete()) {
            return "";
        } else {
            return ":treasure";
        }
    }

    /**
     * check if there are any treasure on the dungeon
     */
    @Override
    public void update() {
        if (getEntitiesOfType("treasure").size() == 0) {
            setComplete(true);
        }      
    }
}
