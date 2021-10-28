package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;

public abstract class Goal {
    private Boolean complete;
    Dungeon dungeon;

    /**
     * Constructor for Goal class
     */
    public Goal(Dungeon dungeon) {
        this.complete = false;
        this.dungeon = dungeon;
    }
    
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean isComplete() {
        return complete;
    }

    @Override
    public String toString() {
        return "";
    }

    /**
     * Helper method to find all current entities of a type on Dungeon
     * @param type
     * @return List<Entity>
     */
    public List<Entity> getEntitiesOfType(String type) {
        List<Entity> listOfEntity = new ArrayList<Entity>();
        for (int i = 0; i < dungeon.getEntityList().size(); i++) {
            if (dungeon.getEntityList().get(i).getType().equals(type)) {
                listOfEntity.add(dungeon.getEntityList().get(i));
            }
        }
        return listOfEntity;
    }

    public abstract Boolean isLeaf();

    public abstract void update();

}
