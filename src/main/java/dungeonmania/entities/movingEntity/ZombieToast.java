package dungeonmania.entities.movingEntity;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.breakableEntity.Armour;
import dungeonmania.util.Direction;

public class ZombieToast extends MovingEntity {
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

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
        this.hasArmour = Math.random() <= 0.2;
        this.dungeon = dungeon;
        this.setLayer(3);
    }

    /**
     * Method for movement of zombie toast
     */
    @Override
    public void move() {
        Player player = (Player) dungeon.getPlayer();

        if (player.isInvincible()) {
            run(this, dungeon);
        }

        int way = randomise();
        if(way == 1) {move(Direction.UP, this);}
        if(way == 2) {move(Direction.DOWN, this);}
        if(way == 3) {move(Direction.LEFT, this);}
        if(way == 4) {move(Direction.RIGHT, this);}
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

    /**
     * Method to grab the random number for zombie toast movement direction
     * @return
     */
    public int randomise() {
        int random = (int)(Math.random()*4) + 1;
        return random;
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
    
}
