package dungeonmania.entities.staticEntity;

public class Exit extends StaticEntity {

    private boolean isPassable = true;

    public Exit(int x, int y, String type) {
        super(x, y, type);
        setIsPassable(isPassable);
    }
    
}
