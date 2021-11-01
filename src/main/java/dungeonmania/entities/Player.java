package dungeonmania.entities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.entities.PlayerState.*;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.movingEntity.Spider;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends Entity {

    // storing required attributes
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

    /**
     * Constructor for Player with only position
     * @param x
     * @param y
     */
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
        setGameMode("standard");
    }

    /**
     * Constructor for Player with Position and dungeon gameMode
     * sets maxHealth and health accordingly to gameMode specification
     * @param x
     * @param y
     * @param gameMode
     */
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

    /**
     * Method for player engaging with battle with a moving entity
     * Order of attacks:
     *  - Player equips all unique equipments (bow, sword, shield, armour)
     *  - if Player was invincible, enemy is killed in one tick and battle ends
     *  - Player takes damage from enemy
     *      - if player health reaches 0, use one ring if available
     *      - if player dies, battle ends and game is lost
     *  - Player attacks enemy
     *  - Ally attacks enemy
     *      - if enemy health reaches 0, enemy is removed from entityList and dungeon
     *  - Player equipment reduces in one durability
     * @param otherEntity
     */
    public Boolean battle(MovingEntity otherEntity) {
        // check inventory and change states accordingly
        equipCombat();
        // Character Health = Character Health - ((Enemy Health * Enemy Attack Damage) / 10) / protection
        if (isInvincible()) {
            Dungeon.getDungeon().removeEntity(otherEntity);
            return false;
        }
        setHealth(getHealth() - ((otherEntity.getHealth() * otherEntity.getDamage()) / 10) / getProtection());
        if (getHealth() <= 0) {
            oneRingState.removeEffect();
        }
        if (getHealth() <= 0) {
            Dungeon.getDungeon().removeEntity(this);
            return false;
        }
        // Enemy Health = Enemy Health - ((Character Health * Character Attack Damage) / 5)
        otherEntity.setHealth(otherEntity.getHealth() - ((getHealth() * getDamage()) / 5));

        allyAssistBattle(otherEntity);
        if (otherEntity.getHealth() <= 0) {
            if (otherEntity instanceof Spider) {
                Spider.setSpiderNum(Spider.getSpiderNum() - 1);
            }
            Dungeon.getDungeon().removeEntity(otherEntity);
            updateCombatDurability();
            return false;
        }
        updateCombatDurability();
        return true;
    }

    /**
     * Helper method for ally mercenaries attacking enemy
     * @param otherEntity
     */
    public void allyAssistBattle(MovingEntity otherEntity) {
        for (Entity entity : Dungeon.getDungeon().getEntityList()) {
            if (entity instanceof Mercenary) {
                Mercenary mercenary = (Mercenary) entity;
                if (mercenary.IsBribed()) {
                    otherEntity.setHealth(otherEntity.getHealth() - (mercenary.getHealth() * mercenary.getDamage()) / 5);
                }
            }
        }
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
        if (door.isPassable()) {
            setPosition(newPosition.getX(), newPosition.getY());
        }
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

    /**
     * States Methods
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

    public void consumeHealthPotion() {
        Inventory inventory = Dungeon.getDungeon().getInventory();
        inventory.breakItem("health_potion");
        healToFullHealth();
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

    public void setPlayerStates(int invinDur, int invisDur, int bowDur, int armourDur, int swordDur, int shieldDur) {
        if (invinDur > 0) {
            invincibleState.loadDuration(invinDur);
        }
        if (invisDur > 0) {
            invisibleState.loadDuration(invisDur);
        }
        if (bowDur > 0) {
            bowState.loadDuration(bowDur);
        }
        if (armourDur > 0) {
            armourState.loadDuration(armourDur);
        }
        if (swordDur > 0) {
            swordState.loadDuration(swordDur);
        }
        if (shieldDur > 0) {
            shieldState.loadDuration(shieldDur);
        }
    }

    public Boolean isInvincible() {
        return invincibleState.isApplied();
    }

    public Boolean isInvisible() {
        return invisibleState.isApplied();
    }

    public Boolean isBow() {
        return bowState.isApplied();
    }

    public Boolean isSword() {
        return swordState.isApplied();
    }

    public Boolean isShield() {
        return shieldState.isApplied();
    }

    public Boolean isArmour() {
        return armourState.isApplied();
    }

    public Boolean isRing() {
        return oneRingState.isApplied();
    }
    /**
     * Getters and Setters
     */
    public int getInvisibilityDuration() {
        return invisibleState.getDuration();
    }

    public int getInvincibilityDuration() {
        return invincibleState.getDuration();
    }

    public int getBowDurability() {
        return bowState.getDuration();
    }

    public int getSwordDurability() {
        return swordState.getDuration();
    }

    public int getArmourDurability() {
        return armourState.getDuration();
    }

    public int getShieldDurability() {
        return shieldState.getDuration();
    }

    public int getOneRingDurability() {
        return oneRingState.getDuration();
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
