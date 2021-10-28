package dungeonmania.entities.collectableEntity;

public class Treasure extends CollectableEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Treasure(int x, int y) {
        super(x, y, "treasure");
        setId("treasure" + String.valueOf(counter));
        counter++;
    }
    
}
