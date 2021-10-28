package dungeonmania.entities.collectableEntity.potionEntity;

import dungeonmania.entities.collectableEntity.CollectableEntity;

public abstract class PotionEntity extends CollectableEntity {
    // duration of potion in number of ticks
    private int duration;

    public PotionEntity(int x, int y, String type) {
        super(x, y, type);
    }
    
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
}
