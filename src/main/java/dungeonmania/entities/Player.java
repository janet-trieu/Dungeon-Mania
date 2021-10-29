package dungeonmania.entities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.PotionState.NoInvincibleState;
import dungeonmania.entities.PotionState.NoInvisibleState;
import dungeonmania.entities.PotionState.PotionState;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.staticEntity.Boulder;
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
        if (Dungeon.getDungeon().canPlayerGoThrough(newPosition)) {
            List<Entity> list = Dungeon.getDungeon().getEntitiesOnSamePosition(newPosition); 
            for (Entity entity : list) {
                if (entity instanceof Boulder) {
                    Boulder boulder = (Boulder) entity;
                    boulder.push(newPosition, direction);
                    if (!boulder.getPosition().equals(newPosition)) {
                        setPosition(newPosition.getX(), newPosition.getY());
                    }
                } else {
                    setPosition(newPosition.getX(), newPosition.getY());
                }
            }
        }
    }

    public void moveUp() {
        if (Dungeon.getDungeon().canPlayerGoThrough(getPosition().translateBy(Direction.UP))) {
            move(Direction.UP);
        }
    }

    public void moveDown() {
        if (Dungeon.getDungeon().canPlayerGoThrough(getPosition().translateBy(Direction.DOWN))) {
            move(Direction.DOWN);
        }
    }
    public void moveLeft() {
        if (Dungeon.getDungeon().canPlayerGoThrough(getPosition().translateBy(Direction.LEFT))) {
            move(Direction.LEFT);
        }
    }
    public void moveRight() {
        if (Dungeon.getDungeon().canPlayerGoThrough(getPosition().translateBy(Direction.RIGHT))) {
            move(Direction.RIGHT);
        }
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
