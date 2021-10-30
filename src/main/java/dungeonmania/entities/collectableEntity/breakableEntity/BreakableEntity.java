package dungeonmania.entities.collectableEntity.breakableEntity;

import dungeonmania.entities.collectableEntity.CollectableEntity;

public abstract class BreakableEntity extends CollectableEntity {

    // number of ticks required before this entity breaks
    private int durability;

    /**
     * Constructor for a breakable entity
     * @param x position
     * @param y position
     * @param type of entity
     */
    public BreakableEntity(int x, int y, String type) {
        super(x, y, type);
    }

    /**
     * Getter for breakable entity's durability
     * @return
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Setter for breakable entity's durability
     * @param durability
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }

}
