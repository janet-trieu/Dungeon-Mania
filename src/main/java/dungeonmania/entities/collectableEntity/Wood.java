package dungeonmania.entities.collectableEntity;

public class Wood extends CollectableEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
    
    public Wood(int x, int y) {
        super(x, y, "wood");
        setId("Wood" + String.valueOf(counter));
        counter++;
    }

}
