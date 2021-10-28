package dungeonmania.entities.staticEntity;

public class FloorSwitch extends StaticEntity {
    
    private boolean isPassable = false;
    private boolean isInteractable = false;

    public FloorSwitch(int x, int y, String type) {
        super(x, y, type);
        setIsPassable(isPassable);
        setIsInteractable(isInteractable);
    }
    
}
