package dungeonmania.entities.staticEntity;

public class ZombieToastSpawner extends StaticEntity {

    private boolean isInteractable = true;

    public ZombieToastSpawner(int x, int y) {
        super(x, y, "zombie_toast_spawner");
        setIsInteractable(isInteractable);
    }
    
}
