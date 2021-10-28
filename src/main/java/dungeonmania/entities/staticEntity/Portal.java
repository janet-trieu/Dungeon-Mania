package dungeonmania.entities.staticEntity;

public class Portal extends StaticEntity {
    
    private boolean isPassable = true;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Portal(int x, int y) {
        super(x, y, "portal");
        setId("portal" + String.valueOf(counter));
        counter++;
        setPassable(isPassable);
    }
    
}
