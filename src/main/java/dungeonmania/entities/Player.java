package dungeonmania.entities;

import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.util.Direction;

public class Player extends Entity implements Moveable {
    private int health;
    private int maxHealth;
    private PotionState potionState;
    private Boolean isShielded;
    private Boolean respawnable;

    public Player(int x, int y) {
        super(x, y, "player");
    }

    public void battle(MovingEntity otherEntity) {
        // TODO
    }

    public void interact(String entityId) {
        // TODO
    }

    public void moveUp() {move(Direction.UP, this);}
    public void moveDown() {move(Direction.DOWN, this);}
    public void moveLeft() {move(Direction.LEFT, this);}
    public void moveRight() {move(Direction.RIGHT, this);}
}
