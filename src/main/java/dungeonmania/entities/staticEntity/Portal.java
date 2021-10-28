package dungeonmania.entities.staticEntity;

public class Portal extends StaticEntity {
    
    private boolean isPassable = true;
    private boolean isInteractable = false;

    public Portal(int x, int y, String type) {
        super(x, y, type);
        setIsPassable(isPassable);
        setIsInteractable(isInteractable);
    }
    
}
