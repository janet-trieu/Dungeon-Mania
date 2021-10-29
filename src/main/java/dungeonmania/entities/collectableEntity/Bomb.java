package dungeonmania.entities.collectableEntity;

public class Bomb extends CollectableEntity {
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Bomb(int x, int y) {
        super(x, y, "bomb");
        setId("Bomb" + String.valueOf(counter));
        counter++;
    }

    /**
     * Method to achieve the bomb explosion
     * @param bombId
     */
    public void explode(String bombId) {
        // TO-DO
    }

}
