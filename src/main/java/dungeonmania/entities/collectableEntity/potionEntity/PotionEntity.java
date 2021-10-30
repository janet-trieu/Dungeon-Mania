package dungeonmania.entities.collectableEntity.potionEntity;

import dungeonmania.entities.collectableEntity.CollectableEntity;

public abstract class PotionEntity extends CollectableEntity {

    // duration of potion in number of ticks
    private int duration;

    /**
     * Constructor for a potion entity
     * @param x position
     * @param y position
     * @param type of entity
     */
    public PotionEntity(int x, int y, String type) {
        super(x, y, type);
    }
    
    /**
     * Getter for potion's duration
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter for potion's duration
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
}
