package dungeonmania.entities.staticEntity;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;

public abstract class StaticEntity extends Entity {
    // Static entities will always be in layer 0 (exception for boulder)
    private int layer = 0;

    // Player being able to pass through static entities are set as false as default
    private boolean isPassable = false;

    // storing a list of static entities
    private List<StaticEntity> staticEntityList = new ArrayList<StaticEntity>();

    public StaticEntity(int x, int y, String type) {
        super(x, y, type);
        setLayer(layer);
        staticEntityList.add(this);
    }
    
    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean isPassable) {
        this.isPassable = isPassable;
    }

    public List<StaticEntity> getStaticEntityList() {
        return staticEntityList;
    }

    public void setStaticEntityList(List<StaticEntity> staticEntityList) {
        this.staticEntityList = staticEntityList;
    }

    
}
