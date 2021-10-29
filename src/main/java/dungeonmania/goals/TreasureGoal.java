package dungeonmania.goals;


import dungeonmania.Dungeon;

public class TreasureGoal extends LeafGoal {
    public TreasureGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String toString() {
        if (isComplete()) {
            return "";
        } else {
            return ":treasure";
        }
    }

    @Override
    public void update() {
        if (getEntitiesOfType("treasure").size() == 0) {
            setComplete(true);
        }      
    }
}
