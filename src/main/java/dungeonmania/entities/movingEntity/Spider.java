package dungeonmania.entities.movingEntity;
import java.util.List;

import dungeonmania.util.*;

public class Spider extends MovingEntity implements Moveable {
    
    private int movementCounter = 0;
    private String way = "Right";

    public Spider(int x, int y) {
        super(x, y, "spider", 1, 10);
    }

    public void move() {
        if(movementCounter == 0) {
            move(Direction.UP, this);
            increment();
        }

        if(way == "Right") {
            if(movementCounter == 1 || movementCounter == 8) {move(Direction.RIGHT, this);}
            else if(movementCounter == 2 || movementCounter == 3) {move(Direction.DOWN, this);}
            else if(movementCounter == 4 || movementCounter == 5) {move(Direction.LEFT, this);}
            else if(movementCounter == 6 || movementCounter == 7) {move(Direction.UP,this);}
            increment();
        }

        if(way == "Left") {
            if(movementCounter == 5 || movementCounter == 6) {move(Direction.RIGHT, this);}
            else if(movementCounter == 7 || movementCounter == 8) {move(Direction.DOWN, this);}
            else if(movementCounter == 1 || movementCounter == 2) {move(Direction.LEFT, this);}
            else if(movementCounter == 3 || movementCounter == 4) {move(Direction.UP,this);}
            increment();
        }

    }

    public void increment() {
        if (movementCounter == 8) {
            this.movementCounter = 1;
            return;
        }
        this.movementCounter = movementCounter + 1;
    }
}
