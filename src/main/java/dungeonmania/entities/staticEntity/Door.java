package dungeonmania.entities.staticEntity;

public abstract class Door extends StaticEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Door(int x, int y) {
        super(x, y, "door");
        setId("door" + String.valueOf(counter));
        counter++;
    }

}
