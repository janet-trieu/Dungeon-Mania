package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

public class Shield extends BuildableEntity {

    // durability attribute of shield is set to 5 (ticks)
    private int durability = 5;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a shield
     * @param x position
     * @param y position
     */
    public Shield(int x, int y) {
        super(x, y, "shield");
        setId("Shield" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }

}
