package dungeonmania.entities.collectableEntity;

import dungeonmania.entities.Entity;

public abstract class CollectableEntity extends Entity {

    // Collectable entities will always be in layer 2
    private int collectLayer = 2;

    /**
     * Constructor for a collectable entity
     * @param x position
     * @param y position
     * @param type of entity
     */
    public CollectableEntity(int x, int y, String type) {
        super(x, y, type);
        setLayer(collectLayer);
    }
    
}
