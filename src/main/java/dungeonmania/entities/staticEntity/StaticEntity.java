package dungeonmania.entities.staticEntity;

import dungeonmania.entities.Entity;

public abstract class StaticEntity extends Entity {
    // Static entities will always be in layer 0 (exception for boulder)
    private int layer = 0;

    public StaticEntity(int x, int y, String type) {
        super(x, y, type);
        setLayer(layer);
    }

}
