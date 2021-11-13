package dungeonmania.entities.collectableEntity.breakableEntity;

public class Armour extends BreakableEntity {

    // durability attribute of armour is set to 8 (ticks)
    public final static int durability = 8;
    public final static int protection = 2;

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
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Armour.counter = counter;
    }
}
