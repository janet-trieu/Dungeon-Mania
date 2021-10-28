package dungeonmania.entities.staticEntity;

public class Wall extends StaticEntity {

    private boolean isPassable = false;
    private boolean isInteractable = false;

    public Wall(int x, int y, String type) {
        super(x, y, type);
        setIsPassable(isPassable);
        setIsInteractable(isInteractable);
    }
    
}
