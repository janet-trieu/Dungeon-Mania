package dungeonmania.entities.movingEntity;

import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.breakableEntity.Armour;
import dungeonmania.util.Direction;

public class ZombieToast extends MovingEntity {
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
    private Random random;
    
    // boolean to check if the spawned zombie toast has armour
    Boolean hasArmour;

    // getting the dungeon instance
    Dungeon dungeon;

    /**
     * Constructor for a zombie toast
     * @param x position
     * @param y position
     * @param dungeon
     */
    public ZombieToast(int x, int y, Dungeon dungeon) {
        super(x, y, "zombie_toast", 5, 5);
        setId("ZombieToast" + String.valueOf(counter));
        counter++;
        random = new Random(System.currentTimeMillis());
        this.hasArmour = Math.random() <= 0.2;
        this.dungeon = dungeon;
        this.setLayer(3);
        if (checkSpawn(dungeon) != null) {
            setDebuff(checkSpawn(dungeon).getMovementFactor() - 1);
        }
    }

    public ZombieToast(int x, int y, Dungeon dungeon, String type) {
        super(x, y, type, 30, 5);
        this.hasArmour = Math.random() <= 0.2;
        this.dungeon = dungeon;
        this.setLayer(3);
        random = new Random(System.currentTimeMillis());
        if (checkSpawn(dungeon) != null) {
            setDebuff(checkSpawn(dungeon).getMovementFactor() - 1);
        }
    }

    /**
     * Method for movement of zombie toast
     */
    @Override
    public void move() {
        if(this.getDebuff() != 0) {
            setDebuff(this.getDebuff() - 1);
            return;
        }
        Player player = (Player) dungeon.getPlayer();

        if (player.isInvincible()) {
            run(this, dungeon);
            return;
        }

        int way = random.nextInt(100) % 4;
        if (way == 0) {move(Direction.UP, this);}
        if (way == 1) {move(Direction.DOWN, this);}
        if (way == 2) {move(Direction.LEFT, this);}
        if (way == 3) {move(Direction.RIGHT, this);}
    }

    /**
     * Method for the zombie toast to drop armour
     */
    public void dropArmour() {
        if (!hasArmour) {
            return;
        }
        Armour armour = new Armour(this.getX(), this.getY());
        dungeon.addItem(armour);
    }


    public Boolean getHasArmour() {
        return hasArmour;
    }

    public void setHasArmour(Boolean hasArmour) {
        this.hasArmour = hasArmour;
    }
    
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        ZombieToast.counter = counter;
    }
    
    public void setSeed(int seed) {
        random = new Random(seed);
    }

    public Random getRandom() {
        return random;
    }
}
