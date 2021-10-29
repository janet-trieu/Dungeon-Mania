package dungeonmania.entities.staticEntity;

import java.util.List;
import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {

    // player is able to interact with zombie toast spawner to destroy it
    private boolean isInteractable = true;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // storing the number of ticks required for a zombie toast spawner to spawn a zombie toast
    private int hardModeTick = 15;
    private int standardTick = 20;

    /**
     * Constructor for a zombie toast spawner
     * @param x position
     * @param y position
     */
    public ZombieToastSpawner(int x, int y) {
        super(x, y, "zombie_toast_spawner");
        setId("ZombieToastSpawner" + String.valueOf(counter));
        counter++;
        setIsInteractable(isInteractable);
    }

    /**
     * Method for spawning a zombie toast in an adjacent cell of zombie toast spawner
     * @param spawner
     * @param dungeon
     */
    public void spawnZombieToast(ZombieToastSpawner spawner, Dungeon dungeon) {
        List<Entity> existingEntities = Dungeon.getDungeon().getEntityList();
        List<Position> spawnableCellList = spawner.getPosition().getAdjacentPositions();

        // to make sure the zombie spawn is randomised, use a random number generator
        // to get the random adjacent position of zombie spawn
        int random = (int)(Math.random() * 8);
        Position randomSpawnCell = spawnableCellList.get(random);

        int i = 0;
        while (existingEntities.contains(spawner)) {
            if (dungeon.getGamemode().equals("Hard")) {
                if (i % hardModeTick == 0) {
                    ZombieToast zombieToast = new ZombieToast(randomSpawnCell.getX(), randomSpawnCell.getY());
                    dungeon.addEntity(zombieToast);
                }
            } else {
                if (i % standardTick == 0) {
                    ZombieToast zombieToast = new ZombieToast(randomSpawnCell.getX(), randomSpawnCell.getY());
                    dungeon.addEntity(zombieToast);
                }
            }
            i++;
        }
    }
    
}
