package dungeonmania.goals;

import dungeonmania.Dungeon;

public abstract class LeafGoal extends Goal {

    public LeafGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public Boolean isLeaf() {
        return true;
    }

}
