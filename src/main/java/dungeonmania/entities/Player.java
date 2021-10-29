package dungeonmania.entities;

import dungeonmania.entities.PotionState.InvincibleState;
import dungeonmania.entities.PotionState.InvisibleState;
import dungeonmania.entities.PotionState.NoInvincibleState;
import dungeonmania.entities.PotionState.NoInvisibleState;
import dungeonmania.entities.PotionState.PotionState;
import dungeonmania.entities.movingEntity.Moveable;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.util.Direction;

public class Player extends Entity implements Moveable {
    private int health;
    private int maxHealth = 10;
    private PotionState invisibleState;
    private PotionState invincibleState;
    private Boolean isShielded;
    private Boolean respawnable;

    public Player(int x, int y) {
        super(x, y, "player");
        invisibleState = new NoInvisibleState(this);
        invincibleState = new NoInvincibleState(this);
        setHealth(maxHealth);
        setId("player");
    }

    public void battle(MovingEntity otherEntity) {
        // TODO
    }

    public void interact(String entityId) {
        // TODO
    }

    public void moveUp() {
        move(Direction.UP, this);
    }

    public void moveDown() {
        move(Direction.DOWN, this);
    }

    public void moveLeft() {
        move(Direction.LEFT, this);
    }
    
    public void moveRight() {
        move(Direction.RIGHT, this);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void changeInvisibleState(PotionState state) {
        this.invisibleState = state;
    }

    public void changeInvincibleState(PotionState state) {
        this.invincibleState = state;
    }

    public void consumeInvisibilityPotion() {
        invisibleState.applyEffect();
    }
    public void consumeInvincibilityPotion() {
        invincibleState.applyEffect();
    }

    public PotionState getInvisibleState() {
        return invisibleState;
    }

    public PotionState getInvincibleState() {
        return invincibleState;
    }

    public Boolean getIsShielded() {
        return isShielded;
    }

    public void setIsShielded(Boolean isShielded) {
        this.isShielded = isShielded;
    }

    public Boolean getRespawnable() {
        return respawnable;
    }

    public void setRespawnable(Boolean respawnable) {
        this.respawnable = respawnable;
    }
    
}
