package dungeonmania.entities.movingEntity;

<<<<<<< HEAD
import dungeonmania.util.Direction;

public class ZombieToast extends MovingEntity implements Moveable {
    
=======
public class ZombieToast extends MovingEntity {
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
>>>>>>> a8a4937af071013297b94a6e93b5e226c6c1afe0
    Boolean hasArmour;

    public ZombieToast(int x, int y) {
        super(x, y, "zombie_toast", 5, 5);
        setId("ZombieToast" + String.valueOf(counter));
        counter++;
    }

    public void move() {
        int way = randomise();
        if(way == 1) {move(Direction.UP, this);}
        if(way == 2) {move(Direction.DOWN, this);}
        if(way == 3) {move(Direction.LEFT, this);}
        if(way == 4) {move(Direction.RIGHT, this);}
    }

    public void dropArmour() {

    }

    public int randomise() {
        int random = (int)(Math.random()*4) + 1;
        return random;
    }
}
