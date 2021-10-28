package dungeonmania.entities.collectableEntity;

public class Key extends CollectableEntity {
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Key(int x, int y) {
        super(x, y, "key");
        setId("key" + String.valueOf(counter));
        counter++;
    }

}
