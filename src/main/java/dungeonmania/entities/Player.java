package dungeonmania.entities;

import dungeonmania.entities.PotionState.InvincibleState;
import dungeonmania.entities.PotionState.InvisibleState;
import dungeonmania.entities.PotionState.NoInvincibleState;
import dungeonmania.entities.PotionState.NoInvisibleState;
import dungeonmania.entities.PotionState.PotionState;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends Entity {
    private int health;
    private int maxHealth = 10;
    private int layer = 4;
    private PotionState invisibleState;
    private PotionState invincibleState;
    private Boolean isShielded;
    private Boolean respawnable;
    private int layer = 4;

    public Player(int x, int y) {
        super(x, y, "player");
        invisibleState = new NoInvisibleState(this);
        invincibleState = new NoInvincibleState(this);
        setHealth(maxHealth);
        setId("Player");
        setLayer(layer);
    }

    public void battle(MovingEntity otherEntity) {
        // TODO
    }

    public void interact(String entityId) {
        // TODO
    }

    public void move(Direction direction) {
        Position newPosition = this.getPosition().translateBy(direction);
        setPosition(newPosition.getX(), newPosition.getY());
    }

    public void moveUp() {
        setY(getY() - 1);
    }

    public void moveDown() {
        setY(getY() + 1);
    }
    public void moveLeft() {
        setX(getX() + 1);
    }
    public void moveRight() {
        setX(getX() - 1);
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
