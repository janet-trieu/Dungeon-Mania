package dungeonmania.entities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.PotionState.NoInvincibleState;
import dungeonmania.entities.PotionState.NoInvisibleState;
import dungeonmania.entities.PotionState.PotionState;
import dungeonmania.entities.movingEntity.Moveable;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.staticEntity.Boulder;
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
<<<<<<< HEAD
        setId("player");
=======
        setId("Player");
        setLayer(layer);
>>>>>>> a8a4937af071013297b94a6e93b5e226c6c1afe0
    }

    public void battle(MovingEntity otherEntity) {
        // TODO
    }

    public void interact(String entityId) {
        // TODO
    }

    public void move(Direction direction) {
        Position newPosition = this.getPosition().translateBy(direction);
        Boolean boulderBoolean = false;
        // if the player can go through in new position or a boulder is at new position
        if (Dungeon.getDungeon().canPlayerGoThrough(newPosition)) {
            // get a list of all entities on new position
            List<Entity> list = Dungeon.getDungeon().getEntitiesOnSamePosition(newPosition); 
            for (Entity entity : list) {
                // if there is a boulder
                if (entity instanceof Boulder) {
                    Boulder boulder = (Boulder) entity;
                    boulderBoolean = true;
                    // push boulder
                    boulder.push(newPosition, direction);
                    // if boulder moves, change position to new Position
                    if (!boulder.getPosition().equals(newPosition)) {
                        setPosition(newPosition.getX(), newPosition.getY());
                        System.out.println("boulder moved");
                    }
                }
            }
            if (boulderBoolean == false) {
                setPosition(newPosition.getX(), newPosition.getY());
            }
        }        
    }

    public void moveUp() {
        if (Dungeon.getDungeon().canPlayerGoThrough(getPosition().translateBy(Direction.UP))) {
            System.out.println("tried to move up");
            move(Direction.UP);
            System.out.println(getPosition());
        }
    }

    public void moveDown() {
        if (Dungeon.getDungeon().canPlayerGoThrough(getPosition().translateBy(Direction.DOWN))) {
            System.out.println("tried to move down");
            move(Direction.DOWN);
            System.out.println(getPosition());
        }
    }

    public void moveLeft() {
        if (Dungeon.getDungeon().canPlayerGoThrough(getPosition().translateBy(Direction.LEFT))) {
            System.out.println("tried to move left");
            move(Direction.LEFT);
            System.out.println(getPosition());
        }
    }
    
    public void moveRight() {
        if (Dungeon.getDungeon().canPlayerGoThrough(getPosition().translateBy(Direction.RIGHT))) {
            System.out.println("tried to move right");
            move(Direction.RIGHT);
            System.out.println(getPosition());
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
