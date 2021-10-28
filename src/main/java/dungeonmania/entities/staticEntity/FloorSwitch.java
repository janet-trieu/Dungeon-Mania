package dungeonmania.entities.staticEntity;

public class FloorSwitch extends StaticEntity {
    
    private boolean isPassable = false;

    public FloorSwitch(int x, int y, String type) {
        super(x, y, type);
        setIsPassable(isPassable);
    }
    
}
