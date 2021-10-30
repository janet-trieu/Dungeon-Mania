package dungeonmania.entities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.PlayerState.NoInvincibleState;
import dungeonmania.entities.PlayerState.NoInvisibleState;
import dungeonmania.entities.PlayerState.PlayerState;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends Entity {
    private double health;
    private double standardMaxHealth = 10;
    private double hardMaxHealth = standardMaxHealth * 8/10;
    private double maxHealth;
    private double damage;
    private String gameMode;
    private int layer = 4;
    private PlayerState invisibleState;
    private PlayerState invincibleState;
    private Boolean isShielded;
    private Boolean respawnable;

    public Player(int x, int y) {
        super(x, y, "player");
        invisibleState = new NoInvisibleState(this);
        invincibleState = new NoInvincibleState(this);
        setId("Player");
        setLayer(layer);
        setHealth(standardMaxHealth);
    }

    public Player(int x, int y, String gameMode) {
        super(x, y, "player");
        invisibleState = new NoInvisibleState(this);
        invincibleState = new NoInvincibleState(this);
        setId("Player");
        setLayer(layer);
        setDamage(1);
        if (gameMode.equals("Hard")) {
            setHealth(hardMaxHealth);
            this.maxHealth = hardMaxHealth;
        } else {
            setHealth(standardMaxHealth);
            this.maxHealth = standardMaxHealth;
        }
        this.gameMode = gameMode;
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

    public double getHealth() {
        return health;
    }

    public void setHealth(double standardMaxHealth2) {
        this.health = standardMaxHealth2;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void changeInvisibleState(PlayerState state) {
        this.invisibleState = state;
    }

    public void changeInvincibleState(PlayerState state) {
        this.invincibleState = state;
    }

    public void consumeInvisibilityPotion() {
        invisibleState.applyEffect();
    }
    public void consumeInvincibilityPotion() {
        invincibleState.applyEffect();
    }

    public void updatePotionDuration() {
        invisibleState.reduceDuration();
        invincibleState.reduceDuration();
    }

    /*
    public Boolean isInvisible() {
        return invisibleState.isInvisible();
    }
*/
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

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
}
