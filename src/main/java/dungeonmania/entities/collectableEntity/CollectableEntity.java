package dungeonmania.entities.collectableEntity;

import dungeonmania.entities.Entity;

public abstract class CollectableEntity extends Entity {
    // Collectable entities will always be in layer 2
    private int layer = 2;

    public CollectableEntity(int x, int y, String type) {
        super(x, y, type);
        setLayer(layer);
    }
    
}
