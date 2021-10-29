package dungeonmania.entities.movingEntity;

import dungeonmania.util.Direction;

public class ZombieToast extends MovingEntity implements Moveable {
    
    Boolean hasArmour;

    public ZombieToast(int x, int y) {
        super(x, y, "zombie_toast", 5, 5);
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
