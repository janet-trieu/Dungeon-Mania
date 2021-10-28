package dungeonmania.entities;

import dungeonmania.entities.movingEntity.MovingEntity;

public class Player extends Entity {
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

    public void moveUp() {
        // TODO
    }

    public void moveDown() {
        // TODO
    }
    public void moveLeft() {
        // TODO
    }
    public void moveRight() {
        // TODO
    }
}
