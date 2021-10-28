package dungeonmania.entities.collectableEntity.breakableEntity;

import dungeonmania.entities.collectableEntity.CollectableEntity;

public abstract class BreakableEntity extends CollectableEntity {
    // number of ticks required before this entity breaks
    private int durability;

    public BreakableEntity(int x, int y, String type) {
        super(x, y, type);
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

}
