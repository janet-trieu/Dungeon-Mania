package dungeonmania.entities.staticEntity;

public class ZombieToastSpawner extends StaticEntity {

    private boolean isInteractable = true;

    public ZombieToastSpawner(int x, int y, String type) {
        super(x, y, type);
        setIsInteractable(isInteractable);
    }
    
}
