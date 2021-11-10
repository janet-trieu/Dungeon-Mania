package dungeonmania.entities.staticEntity;

public class SwampTile extends StaticEntity {

    // SwampTile is passable for a player
    private boolean isPassable = true;

    private int movementFactor;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a swamp tile
     * @param x position
     * @param y position
     * @param movementFactor how slowed entites are if on the tile
     */
    public SwampTile(int x, int y, int movementFactor) {
        super(x, y, "swamp_tile");
        setMovementFactor(movementFactor);
        setId("SwampTile" + String.valueOf(counter));
        counter++;
        setPassable(isPassable);
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        SwampTile.counter = counter;
    }

    public int getMovementFactor() {
        return movementFactor;
    }

    public void setMovementFactor(int movementFactor) {
        this.movementFactor = movementFactor;
    }
    
}
