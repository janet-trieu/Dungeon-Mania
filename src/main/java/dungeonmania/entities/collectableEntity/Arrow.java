package dungeonmania.entities.collectableEntity;

public class Arrow extends CollectableEntity {
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Arrow(int x, int y) {
        super(x, y, "arrow");
        setId("arrow" + String.valueOf(counter));
        counter++;
    }
    
}
