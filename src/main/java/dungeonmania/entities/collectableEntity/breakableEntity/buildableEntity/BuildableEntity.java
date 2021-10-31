package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

import dungeonmania.entities.collectableEntity.breakableEntity.BreakableEntity;

public abstract class BuildableEntity extends BreakableEntity {
    
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
