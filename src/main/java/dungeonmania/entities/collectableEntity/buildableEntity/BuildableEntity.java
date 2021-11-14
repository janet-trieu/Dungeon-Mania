package dungeonmania.entities.collectableEntity.buildableEntity;

import dungeonmania.Dungeon;
import dungeonmania.entities.collectableEntity.CollectableEntity;

public abstract class BuildableEntity extends CollectableEntity {
    
    // dungeon instance
    Dungeon currDungeon = Dungeon.getDungeon();

    /**
     * Constructor for a buildable entity
     * @param x position
     * @param y position
     * @param type of entity
     */
    public BuildableEntity(int x, int y, String type) {
        super(x, y, type);
    }

    /**
     * Method for removing used ingredients in creating a  buildable entity
     */
    public abstract void useIngredient();
    
}
