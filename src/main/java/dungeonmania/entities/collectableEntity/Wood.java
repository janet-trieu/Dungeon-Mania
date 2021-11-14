package dungeonmania.entities.collectableEntity;

public class Wood extends CollectableEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
    
    /**
     * Constructor for a wood
     * @param x position
     * @param y position
     */
    public Wood(int x, int y) {
        super(x, y, "wood");
        setId("Wood" + String.valueOf(counter));
        counter++;
    }

    public static void setCounter(int counter) {
        Wood.counter = counter;
    }
    
}
