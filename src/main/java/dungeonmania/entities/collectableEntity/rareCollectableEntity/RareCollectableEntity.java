package dungeonmania.entities.collectableEntity.rareCollectableEntity;

import dungeonmania.entities.collectableEntity.CollectableEntity;

public abstract class RareCollectableEntity extends CollectableEntity {
    
    /**
     * Constructor for a rare collectable entity
     * @param x position
     * @param y position
     * @param type of entity
     */
    public RareCollectableEntity(int x, int y, String type) {
        super(x, y, type);
    }
    
}
