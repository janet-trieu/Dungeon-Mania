package dungeonmania.entities.collectableEntity.breakableEntity;

public class Armour extends BreakableEntity {

    // durability attribute of armour is set to 8 (ticks)
    private int durability = 8;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for an armour
     * @param x position
     * @param y position
     */
    public Armour(int x, int y) {
        super(x, y, "armour");
        setId("Armour" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }

}
