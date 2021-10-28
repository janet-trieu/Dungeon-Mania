package dungeonmania.entities.staticEntity;

public class Exit extends StaticEntity {

    private boolean isPassable = true;

    public Exit(int x, int y) {
        super(x, y, "exit");
        setPassable(isPassable);
    }
    
}
