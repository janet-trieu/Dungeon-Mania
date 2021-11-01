package dungeonmania.entities.collectableEntity;

public class Treasure extends CollectableEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a treasure
     * @param x position
     * @param y position
     */
    public Treasure(int x, int y) {
        super(x, y, "treasure");
        setId("Treasure" + String.valueOf(counter));
        counter++;
    }
    
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Treasure.counter = counter;
    }

}
