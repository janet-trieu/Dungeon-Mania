package dungeonmania.entities.staticEntity;

public class Portal extends StaticEntity {
    
    private boolean isPassable = true;

    public Portal(int x, int y) {
        super(x, y, "portal");
        setPassable(isPassable);
    }
    
}
