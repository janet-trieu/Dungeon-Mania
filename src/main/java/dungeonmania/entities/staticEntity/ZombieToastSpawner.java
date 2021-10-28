package dungeonmania.entities.staticEntity;

public class ZombieToastSpawner extends StaticEntity {

    private boolean isInteractable = true;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public ZombieToastSpawner(int x, int y) {
        super(x, y, "zombie_toast_spawner");
        setId("zombie_toast_spawner" + String.valueOf(counter));
        counter++;
        setIsInteractable(isInteractable);
    }
    
}
