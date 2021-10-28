package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

import dungeonmania.entities.collectableEntity.breakableEntity.BreakableEntity;

public abstract class BuildableEntity extends BreakableEntity {
    
    public BuildableEntity(int x, int y, String type) {
        super(x, y, type);
    }
    
}
