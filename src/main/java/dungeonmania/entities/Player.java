package dungeonmania.entities;

import java.util.List;

import dungeonmania.Dungeon;
<<<<<<< HEAD
import dungeonmania.entities.PotionState.NoInvincibleState;
import dungeonmania.entities.PotionState.NoInvisibleState;
import dungeonmania.entities.PotionState.PotionState;
import dungeonmania.entities.movingEntity.Moveable;
=======
import dungeonmania.Inventory;
import dungeonmania.entities.PlayerState.*;
>>>>>>> a20144942dd184b1ea148f5023ad0d77ef2e6557
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.movingEntity.Mercenary;
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
    private double protection;
    private String gameMode;
    private int layer = 4;

    // Player States
    private PlayerState invincibleState;
    private PlayerState invisibleState;
    private PlayerState shieldState;
    private PlayerState armourState;
    private PlayerState swordState;
    private PlayerState bowState;
    private PlayerState oneRingState;

    public Player(int x, int y) {
        super(x, y, "player");
        invincibleState = new NoInvincibleState(this);
        invisibleState = new NoInvisibleState(this);
        oneRingState = new NoOneRingState(this);
        shieldState = new NoShieldState(this);
        armourState = new NoArmourState(this);
        swordState = new NoSwordState(this);
        bowState = new NoBowState(this);
        setId("Player");
        setLayer(layer);
        setDamage(1);
        setProtection(1);
        setHealth(standardMaxHealth);
        setMaxHealth(standardMaxHealth);
    }

    public Player(int x, int y, String gameMode) {
        super(x, y, "player");
        invincibleState = new NoInvincibleState(this);
        invisibleState = new NoInvisibleState(this);
        oneRingState = new NoOneRingState(this);
        shieldState = new NoShieldState(this);
        armourState = new NoArmourState(this);
        swordState = new NoSwordState(this);
        bowState = new NoBowState(this);
        setId("Player");
        setLayer(layer);
        setDamage(1);
        setProtection(1);
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
        // check inventory and change states accordingly
        equipCombat();
        // Character Health = Character Health - ((Enemy Health * Enemy Attack Damage) / 10) / protection
        if (isInvincible()) {
            Dungeon.getDungeon().removeEntity(otherEntity);
            return;
        }
        setHealth(getHealth() - ((otherEntity.getHealth() * otherEntity.getDamage()) / 10) / getProtection());
        if (getHealth() <= 0) {
            oneRingState.removeEffect();
        }
        if (getHealth() <= 0) {
            Dungeon.getDungeon().removeEntity(this);
            return;
        }
        // Enemy Health = Enemy Health - ((Character Health * Character Attack Damage) / 5)
        otherEntity.setHealth(otherEntity.getHealth() - ((getHealth() * getDamage()) / 5));

        if (otherEntity.getHealth() <= 0) {
            Dungeon.getDungeon().removeEntity(otherEntity);
        }
        //allyAssistBattle(otherEntity);
        updateCombatDurability();
    }

    public void allyAssistBattle(MovingEntity otherEntity) {
        for (Entity entity : Dungeon.getDungeon().getEntityList()) {
            if (entity instanceof Mercenary) {
                Mercenary mercenary = (Mercenary) entity;
                if (mercenary.isBribed()) {
                    otherEntity.setHealth(otherEntity.getHealth() - (mercenary.getHealth() * mercenary.getDamage()) / 5);
                }
            }
        }
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
    public void interactPortal(Entity entity, Direction direction, Position currPosition) {
        Portal portal = (Portal) entity;
        Position otherPortalPosition = portal.correspondingPortalPosition();
        if (otherPortalPosition != null) {
            portal.teleport(this, otherPortalPosition);
            move(direction);
            // if position is still at otherPortal, it means theres something blocking the way
            // go back
            if (getPosition().equals(otherPortalPosition)) {
                setPosition(currPosition.getX(), currPosition.getY());
            }
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
        Position currPosition = getPosition();
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
                    interactPortal(entity, direction, currPosition);
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

<<<<<<< HEAD
=======
    /**
     * player moves left
     */
>>>>>>> a20144942dd184b1ea148f5023ad0d77ef2e6557
    public void moveLeft() {
        move(Direction.LEFT);
    }
<<<<<<< HEAD
    
=======

    /**
     * player moves right
     */
>>>>>>> a20144942dd184b1ea148f5023ad0d77ef2e6557
    public void moveRight() {
        move(Direction.RIGHT);
    }

    /**
     * States
     */
    /**
     * Change States
     */
    public void changeInvisibleState(PlayerState state) {
        this.invisibleState = state;
    }

    public void changeInvincibleState(PlayerState state) {
        this.invincibleState = state;
    }

    public void changeArmourState(PlayerState state) {
        this.armourState = state;
    }

    public void changeShieldState(PlayerState state) {
        this.shieldState = state;
    }

    public void changeSwordState(PlayerState state) {
        this.swordState = state;
    }

    public void changeBowState(PlayerState state) {
        this.bowState = state;
    }

    public void changeOneRingState(PlayerState state) {
        this.oneRingState = state;
    }

    /**
     * Apply state
     */
    public void consumeInvisibilityPotion() {
        invisibleState.applyEffect();
    }
    public void consumeInvincibilityPotion() {
        invincibleState.applyEffect();
    }
    public void equipBow() {
        bowState.applyEffect();
    }
    public void equipSword() {
        swordState.applyEffect();
    }
    public void equipShield() {
        shieldState.applyEffect();
    }
    public void equipArmour() {
        armourState.applyEffect();
    }
    public void equipOneRing() {
        oneRingState.applyEffect();
    }

    public void equipCombat() {
        Inventory inventory = Dungeon.getDungeon().getInventory();
        if (inventory.numberOfItem("armour") >= 1) {
            equipArmour();
        }
        if (inventory.numberOfItem("shield") >= 1) {
            equipShield();
        }
        if (inventory.numberOfItem("sword") >= 1) {
            equipSword();
        }
        if (inventory.numberOfItem("bow") >= 1) {
            equipBow();
        }
        if (inventory.numberOfItem("the_one_ring") >= 1) {
            equipOneRing();
        }
    }

    /**
     * Update Duration of items
     */
    public void updatePotionDuration() {
        invisibleState.reduceDuration();
        invincibleState.reduceDuration();
    }

    public void updateCombatDurability() {
        armourState.reduceDuration();
        shieldState.reduceDuration();
        bowState.reduceDuration();
        swordState.reduceDuration();
    }

    public Boolean isInvincible() {
        return invincibleState.isApplied();
    }

    /**
     * Getters and Setters
     */
    public double getHealth() {
        return health;
    }

    public void setHealth(double standardMaxHealth2) {
        this.health = standardMaxHealth2;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double standardMaxHealth2) {
        this.maxHealth = standardMaxHealth2;
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

    public void setDamage(double d) {
        this.damage = d;
    }

    public double getProtection() {
        return protection;
    }

    public void setProtection(double protection) {
        this.protection = protection;
    }

    public void healToFullHealth() {
        this.health = maxHealth;
    }
    
}
