package dungeonmania.entities.movingEntity;

import java.util.List;
import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.Inventory;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.Armour;
import dungeonmania.entities.staticEntity.SwampTile;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.Player;

public class Mercenary extends MovingEntity implements Bribeable, Dijkstra {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // mercenary is interactable with player ( for bribing )
    private boolean isInteractable = true;

    //
    private int mindControlDuration = 0;

    // boolean to check if the spawned mercenary has armour
    private boolean hasArmour;

    // mercenary is spawned as hostile
    private boolean isBribed = false;

    // getting the dungeon instance
    Dungeon dungeon;

    // storing the number of occurences this method is called, to know when to spawn the mercenary
    private static int tickCounter = 0;

    /**
     * Constructor for the mercenary
     * @param x position
     * @param y position
     * @param dungeon 
     */
    public Mercenary(int x, int y, Dungeon dungeon) {
        super(x, y, "mercenary", 10, 1);
        setId("Mercenary" + String.valueOf(counter));
        setIsInteractable(isInteractable);
        counter++;
        this.hasArmour = Math.random() <= 0.2;
        this.dungeon = dungeon;
        this.setLayer(3);
        if (checkSpawn(dungeon) != null) {
            setDebuff(checkSpawn(dungeon).getMovementFactor() - 1);
        }
        super.setDamagepeaceful(dungeon);
    }
    
    /**
     * Method to spawn the mercenary or assassin
     */
    public void spawnMercenary() {
        if (tickCounter >= 25 && hostileInDungeon()) {
            Random random = new Random(System.currentTimeMillis());
            int assOrMerc = random.nextInt(100) % 5;
            if (assOrMerc == 0) {
                Assassin assassin = new Assassin(getX(), getY(), getDungeon());
                dungeon.addEntity(assassin);
            } else {
                dungeon.addEntity(this);
            }
            setTickCounter(0);
        } 
        tickCounter++;
    }


    /**
     * get number of enemies
     */

    public Boolean hostileInDungeon() {
        for (Entity entity : Dungeon.getDungeon().getEntityList()) {
            if (entity instanceof MovingEntity) {
                if (entity instanceof Mercenary && !isMercenaryBribed(entity)) {
                    return true;
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean isMercenaryBribed(Entity entity) {
        Mercenary mercenary = (Mercenary) entity;
        if (mercenary.isBribed()) {
            return true;
        }
        return false;
    }


    /**
     * Method for the mercenary to move
     */
    @Override
    public void move() {
        if(this.getDebuff() > 0) {
            setDebuff(this.getDebuff() - 1);
            return;
        }
        Player player = (Player) dungeon.getPlayer();
        if (player != null && player.isInvincible()) {
            if(!isBribed) {
            run(this, dungeon);
            }
        }
        
        move(dungeon, this);
    
        List<Entity> list = dungeon.getEntitiesOnSamePosition(getPosition());
        for(Entity current : list) {
            if (current instanceof SwampTile) {
                SwampTile tile = (SwampTile) current;
                setDebuff(tile.getMovementFactor() - 1);
            }
        }
    }
    /**
     * Method for the mercenary to drop armour
     */
    public void dropArmour() {
        if (!hasArmour) {
            return;
        }
        Armour armour = new Armour(this.getX(), this.getY());
        dungeon.addItem(armour);
    }
    
    /**
     * Method to bribe the mercenary with treasure to become an ally 
     */
    @Override
    public void bribe() {
        Inventory inventory = dungeon.getInventory();
        if (isBribed()) {
            return;
        }

        // if player has sceptre, used as priority to mind control mercenary
        if (inventory.numberOfItem("sceptre") > 0) {
            setMindControlDuration(10);
            setIsBribed(true);
            setIsInteractable(false);
            return;
        }
        
        // if player has sun stone, use to bribe mercenary
        if (inventory.numberOfItem("sun_stone") > 0) {
            setIsBribed(true);
            setIsInteractable(false);
            return;

        // else, player uses treasure to bribe mercenary    
        } else if (inventory.numberOfItem("treasure") > 0) {
            setIsBribed(true);
            inventory.breakItem("treasure");
            setIsInteractable(false);
            return;

        // player cannot bribe mercenary    
        } else {
            throw new InvalidActionException("Cannot bribe without treasure or sun stone");
        }
    }

    @Override
    public void updateMindControl() {
        if (getMindControlDuration() == 0) {
            setIsInteractable(true);
            setIsBribed(false);
        }
    }

    @Override
    public boolean isMindControlled() {
        if (getMindControlDuration() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isBribed() {
        return isBribed;
    }

    @Override
    public void setIsBribed(boolean isBribed) {
        this.isBribed = isBribed;
    }

    public Boolean getHasArmour() {
        return hasArmour;
    }

    public void setHasArmour(Boolean hasArmour) {
        this.hasArmour = hasArmour;
    }

    public static void setCounter(int counter) {
        Mercenary.counter = counter;
    }

    public static int getTickCounter() {
        return tickCounter;
    }

    public static void setTickCounter(int tickCounter) {
        Mercenary.tickCounter = tickCounter;
    }

    @Override
    public int getMindControlDuration() {
        return mindControlDuration;
    }

    @Override
    public void setMindControlDuration(int mindControlDuration) {
        this.mindControlDuration = mindControlDuration;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

}
