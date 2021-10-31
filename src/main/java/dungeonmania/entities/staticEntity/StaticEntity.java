package dungeonmania.entities.staticEntity;

import dungeonmania.entities.Entity;

public abstract class StaticEntity extends Entity {
    
    // Static entities will always be in layer 0 (exception for boulder)
    private int layer = 0;

    // Player being able to pass through static entities are set as false as default
    private boolean isPassable = false;

    public StaticEntity(int x, int y, String type) {
        super(x, y, type);
        setLayer(layer);
    }
    
    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean isPassable) {
        this.isPassable = isPassable;
    }

}
