package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;

public abstract class Goal {

    // storing the required attributes
    private Boolean complete;
    private Dungeon dungeon;

    /**
     * Constructor for Goal class
     */
    public Goal(Dungeon dungeon) {
        this.complete = false;
        this.dungeon = dungeon;
    }
    
    /**
     * Abstract methods
     */
    @Override
    public String toString() {
        return "";
    }
    
    public abstract void update();
    
    /**
     * Getters and Setters
     * 
     */
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
    
    public Boolean isComplete() {
        return complete;
    }
    
    public Dungeon getDungeon() {
        return dungeon;
    }
    
    /**
     * Helper method to find all current entities of a type on Dungeon
     * @param type
     * @return List<Entity>
     */
    public List<Entity> getEntitiesOfType(String type) {
        List<Entity> listOfTypeEntity = new ArrayList<Entity>();
        List<Entity> entityList = dungeon.getEntityList();
        for (Entity entity : entityList) {
            if (entity.getType().equals(type)) {
                listOfTypeEntity.add(entity);
            }
        }
        return listOfTypeEntity;
    }
    
}
