package dungeonmania.entities.collectableEntity.rareCollectableEntity;

public class Anduril extends RareCollectableEntity {
    public final static int damage = 2;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for Anduril
     * @param x
     * @param y
     */
    public Anduril(int x, int y) {
        super(x, y, "anduril");
        setId("Anduril" + String.valueOf(counter));
        counter++;
    }

    public static void setCounter(int counter) {
        Anduril.counter = counter;
    }
    
}
