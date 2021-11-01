package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.breakableEntity.Armour;
import dungeonmania.entities.collectableEntity.breakableEntity.Sword;
import dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity.Bow;
import dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity.Shield;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Spider;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTest {
    @Test
    public void testBattleSpider(){
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create spider at (1, 0)
        Spider spider = new Spider(1, 0, dungeon);
        dungeon.addEntity(spider);
        // battle spider
        player.moveRight();
        player.battle(spider);
        // spider is dead
        assertEquals(false, dungeon.getEntityList().contains(spider));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
    }

    @Test
    public void testBattleZombieToast(){
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create zombieToast at (1, 0)
        ZombieToast zombieToast = new ZombieToast(1, 0, dungeon);
        dungeon.addEntity(zombieToast);
        // battle ZombieToast
        player.moveRight();
        player.battle(zombieToast);
        // zombieToast is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(zombieToast));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 4; i++) {
            player.battle(zombieToast);
        }
        // zombieToast is not alive
        assertEquals(false, dungeon.getEntityList().contains(zombieToast));
    }
    @Test
    public void testMercenary(){
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 6; i++) {
            player.battle(mercenary);
        }
        // mercenary is not alive
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }

    @Test
    public void testBow(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add bow to inventory
        Bow bow = new Bow(-1, -1);
        inventory.addItem(bow);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 2; i++) {
            player.battle(mercenary);
        }
        // mercenary is not alive after 3 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testSword(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add sword to inventory
        Sword sword = new Sword(-1, -1);
        inventory.addItem(sword);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 2; i++) {
            player.battle(mercenary);
        }
        // mercenary is not alive after 3 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testShield(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add shield to inventory
        Shield shield = new Shield(-1, -1);
        inventory.addItem(shield);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 5; i++) {
            player.battle(mercenary);
        }
        // mercenary is not alive after 6 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testArmour(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add Armour to inventory
        Armour armour = new Armour(-1, -1);
        inventory.addItem(armour);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 5; i++) {
            player.battle(mercenary);
        }
        // mercenary is not alive after 6 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testBowAndSword(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add bow to inventory
        Bow bow = new Bow(-1, -1);
        inventory.addItem(bow);

        // Create and add sword to inventory
        Sword sword = new Sword(-1, -1);
        inventory.addItem(sword);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        player.battle(mercenary);
        // mercenary is not alive after 2 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testArmourAndShield(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add Armour to inventory
        Armour armour = new Armour(-1, -1);
        inventory.addItem(armour);

        // Create and add shield to inventory
        Shield shield = new Shield(-1, -1);
        inventory.addItem(shield);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 5; i++) {
            player.battle(mercenary);
        }
        // mercenary is not alive after 6 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testBowAndSwordAndArmourAndShield(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add bow to inventory
        Bow bow = new Bow(-1, -1);
        inventory.addItem(bow);

        // Create and add sword to inventory
        Sword sword = new Sword(-1, -1);
        inventory.addItem(sword);

        // Create and add Armour to inventory
        Armour armour = new Armour(-1, -1);
        inventory.addItem(armour);

        // Create and add shield to inventory
        Shield shield = new Shield(-1, -1);
        inventory.addItem(shield);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        player.battle(mercenary);

        // mercenary is not alive after 1 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testPlayerDies(){
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create mercenary0 at (1, 0)
        Mercenary mercenary0 = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary0);
 
        // Create mercenary1 at (2, 0)
        Mercenary mercenary1 = new Mercenary(2, 0, dungeon);
        dungeon.addEntity(mercenary1);       

        // battle mercenary0
        player.moveRight();
        player.battle(mercenary0);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary0));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 6; i++) {
            player.battle(mercenary0);
        }
        // mercenary is dead
        assertEquals(false, dungeon.getEntityList().contains(mercenary0));
        // battle mercenary1
        player.moveRight();
        player.battle(mercenary1);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary1));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 8; i++) {
            player.battle(mercenary1);
        }
        // player battling mercenary0 and mercenary1 consecutively
        assertEquals(false, dungeon.getEntityList().contains(player));
    }
        /**
     * Testing for ZombieToastSpawner and Wall functionality in Standard
     * - Wall stays put
     * - ZombieToast spawns every 20 ticks in Standard
     */
    
    @Test
    public void testBribe() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("bribe", "Standard");

        // 20 TICKS
        controller.tick(null, Direction.RIGHT);

        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(3,0,3), true), controller.getInfo("Mercenary0"));
        controller.interact("Mercenary0");
        controller.tick(null, Direction.RIGHT);
        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(2,0,3), true), controller.getInfo("Mercenary0"));
    }

    @Test
    public void testBribeTooFar() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("bribe-far", "Standard");

        controller.tick(null, Direction.RIGHT);

        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(4,0,3), true), controller.getInfo("Mercenary0"));
        assertThrows(InvalidActionException.class, () -> controller.interact("Mercenary0"));
        controller.tick(null, Direction.RIGHT);
        controller.interact("Mercenary0");
    }
    @Test
    public void testBribeNoTreasure() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("bribe-far", "Standard");
        
        controller.tick(null, Direction.DOWN);

        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(5,1,3), true), controller.getInfo("Mercenary0"));
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.interact("Mercenary0"));
    }
}
