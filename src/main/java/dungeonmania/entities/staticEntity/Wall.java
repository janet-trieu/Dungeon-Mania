package dungeonmania.entities.staticEntity;

public class Wall extends StaticEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constuctor for a wall
     * @param x position
     * @param y position
     */
    public Wall(int x, int y) {
        super(x, y, "wall");
        setId("Wall" + String.valueOf(counter));
        counter++;
    }
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Wall.counter = counter;
    }
}
