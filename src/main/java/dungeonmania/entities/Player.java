package dungeonmania.entities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.PotionState.NoInvincibleState;
import dungeonmania.entities.PotionState.NoInvisibleState;
import dungeonmania.entities.PotionState.PotionState;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.Portal;
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

    /**
     * Player interaction with boulder
     * either pushes boulder and moves to newPosition or stays put
     * @param entity
     * @param newPosition
     * @param direction
     */
    public void interactBoulder(Entity entity, Position newPosition, Direction direction) {
        Boulder boulder = (Boulder) entity;
        // push boulder
        boulder.push(newPosition, direction);
        // if boulder moves, change position to new Position
        if (!boulder.getPosition().equals(newPosition)) {
            setPosition(newPosition.getX(), newPosition.getY());
        }
    }

    /**
     * Player interaction with Door
     * either unlocks door and moves to newPosition or stays put
     * @param entity
     * @param newPosition
     */
    public void interactDoor(Entity entity, Position newPosition) {
        Door door = (Door) entity;
        // If player had key, open door and discard key
        Key key = Dungeon.getDungeon().getKey();
        if (key != null && door.insertKey(key)) {
            setPosition(newPosition.getX(), newPosition.getY());
            Dungeon.getDungeon().removeItem(key);
        } 
    }

    /**
     * Player interaction with Portal
     * either teleports to correspondingPortal or stays put
     * @param entity
     */
    public void interactPortal(Entity entity) {
        Portal portal = (Portal) entity;
        Position otherPortalPosition = portal.correspondingPortalPosition();
        if (otherPortalPosition != null) {
            portal.teleport(this, otherPortalPosition);
        }
    }

    /**
     * player picks up all items on current position
     * @param position
     */
    public void pickupItems(Position position) {
        Dungeon dungeon = Dungeon.getDungeon();
        List<Entity> list = dungeon.getEntitiesOnSamePosition(position); 
        for (Entity entity : list) {
            // if collectable, pick up item
            if (entity instanceof CollectableEntity) {
                CollectableEntity collectable = (CollectableEntity) entity;
                if (Dungeon.getDungeon().addItem(collectable)) {
                    collectable.setPosition(-1, -1);
                    dungeon.removeEntity(collectable);
                }
            }
        }
    }

    /**
     * Player moves in a specific direction
     * Player interacts with entities in that new position
     * - Boulders
     * - Door
     * - Portals
     * - Collectable Entities
     * @param direction
     */
    public void move(Direction direction) {
        Dungeon dungeon = Dungeon.getDungeon();
        Position newPosition = this.getPosition().translateBy(direction);
        Boolean alreadyMoved = false;
        List<Entity> list = dungeon.getEntitiesOnSamePosition(newPosition); 
        // if the player can go through in new position or a boulder is at new position
        if (dungeon.canPlayerGoThrough(newPosition)) {
            // get a list of all entities on new position
            // interact with static entities first
            for (Entity entity : list) {
                // if boulder, attempt to move
                if (entity instanceof Boulder) {
                    interactBoulder(entity, newPosition, direction);
                    alreadyMoved = true;
                }
                // if there is a door
                if (entity instanceof Door) {
                    interactDoor(entity, newPosition);
                    if (!getPosition().equals(newPosition)) {
                        alreadyMoved = true;
                    }
                }
                // if portal, attempt to teleport
                if (entity instanceof Portal) {
                    interactPortal(entity);
                    if (!getPosition().equals(newPosition)) {
                        alreadyMoved = true;
                    }
                }
            }
            if (alreadyMoved == false) {
                setPosition(newPosition.getX(), newPosition.getY());
            }
            // pick up any items
            pickupItems(getPosition());
        } 
    }

    /**
     * player moves up
     */
    public void moveUp() {
        move(Direction.UP);
    }

    /**
     * player moves down
     */
    public void moveDown() {
        move(Direction.DOWN);
    }

    /**
     * player moves left
     */
    public void moveLeft() {
        move(Direction.LEFT);
    }

    /**
     * player moves right
     */
    public void moveRight() {
        move(Direction.RIGHT);
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
