package dungeonmania.entities.collectableEntity.rareCollectableEntity;

public class TheOneRing extends RareCollectableEntity {
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for the one ring
     * @param x position
     * @param y position
     */
    public TheOneRing(int x, int y) {
        super(x, y, "the_one_ring");
        setId("TheOneRing" + String.valueOf(counter));
        counter++;
    }

    public static void setCounter(int counter) {
        TheOneRing.counter = counter;
    }
    
}
