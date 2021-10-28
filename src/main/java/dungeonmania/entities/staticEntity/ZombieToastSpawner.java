package dungeonmania.entities.staticEntity;

public class ZombieToastSpawner extends StaticEntity {
    private boolean isPassable = false;
    private boolean isInteractable = true;

    public ZombieToastSpawner(int x, int y, String type) {
        super(x, y, type);
        setIsPassable(isPassable);
        setIsInteractable(isInteractable);
    }
    
}
