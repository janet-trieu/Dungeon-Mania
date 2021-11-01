package dungeonmania.entities.staticEntity;

public class Exit extends StaticEntity {

    // exit is passable for a player
    private boolean isPassable = true;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for an exit
     * @param x position
     * @param y position
     */
    public Exit(int x, int y) {
        super(x, y, "exit");
        setId("Exit" + String.valueOf(counter));
        counter++;
        setPassable(isPassable);
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Exit.counter = counter;
    }
    
}
