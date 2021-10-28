package dungeonmania.entities.staticEntity;

public class Exit extends StaticEntity {

    private boolean isPassable = true;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Exit(int x, int y) {
        super(x, y, "exit");
        setId("exit" + String.valueOf(counter));
        counter++;
        setPassable(isPassable);
    }
    
}
