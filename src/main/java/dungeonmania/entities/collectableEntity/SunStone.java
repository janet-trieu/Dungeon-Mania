package dungeonmania.entities.collectableEntity;

public class SunStone extends CollectableEntity {
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a sun stone
     * @param x position
     * @param y position
     */
    public SunStone(int x, int y) {
        super(x, y, "sun_stone");
        setId("SunStone" + String.valueOf(counter));
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        SunStone.counter = counter;
    }
    
}
