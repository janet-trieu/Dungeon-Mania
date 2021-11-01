package dungeonmania.entities.staticEntity;

import java.util.ArrayList;
import java.util.List;

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

    // storing the number of occurences this method is called, to know when to spawn the zombie toast
    private static int tickCounter = 1;

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
    public void spawnZombieToast(Dungeon dungeon) {
        List<Entity> existingEntities = dungeon.getEntityList();
        List<Position> spawnableCellList = new ArrayList<Position>();

        for (Entity entity : existingEntities) {
            if (entity instanceof ZombieToastSpawner)  {
                spawnableCellList = dungeon.getCardinalAdjacentCell(entity);
            }
        }

        // to make sure the zombie spawn is randomised, use a random number generator
        // to get the random adjacent position of zombie spawn
        int random = (int)(Math.random() * 4);
        Position randomSpawnCell = spawnableCellList.get(random);

        if (dungeon.getGameMode().equals("Hard")) {
            if (tickCounter % hardModeTick == 0) {
                ZombieToast zombieToast = new ZombieToast(randomSpawnCell.getX(), randomSpawnCell.getY(), dungeon);
                dungeon.addEntity(zombieToast);
            }
        } else {
            if (tickCounter % standardTick == 0) {
                ZombieToast zombieToast = new ZombieToast(randomSpawnCell.getX(), randomSpawnCell.getY(), dungeon);
                dungeon.addEntity(zombieToast);
            }
        }
        tickCounter++;
    }

    public static int getTickCounter() {
        return tickCounter;
    }

    public static void setTickCounter(int tickCounter) {
        ZombieToastSpawner.tickCounter = tickCounter;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        ZombieToastSpawner.counter = counter;
    }
    
}
