package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    /**
     * Test for bribe mercenary with sun stone
     * - sun stone does not get used up
     */
    @Test
    public void testBribeSunStone() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("testBribeSunStone", "Standard");
        
        // player moves 1 cell to the right to pick up sun stone
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1, true);

        // mercenary started at (4, 0), now is at (3, 0)
        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(3,0,3), true), controller.getInfo("Mercenary0"));

        // attempt to bribe mercenary with sun stone
        assertDoesNotThrow(() -> {
            controller.interact("Mercenary0");
        });

        // sun stone does not get used up
        assertEquals(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1, true);

    }
    
    @Test
    public void testBribeSunStoneFar() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("testBribeFarSunStone", "Standard");
        
        // player moves 1 cell to the right to pick up sun stone
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1, true);

        // mercenary started at (5, 0), now is at (4, 0)
        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(4,0,3), true), controller.getInfo("Mercenary0"));

        // attempt to bribe mercenary with sun stone
        // player is currently at position (1, 0) while mercenary is at (4, 0)
        // player is too far to attempt to bribe mercenary
        assertThrows(InvalidActionException.class, () -> controller.interact("Mercenary0"));

    }

    /**
     * As per our assumption:
     * - When a player interacts with mercenary/assassin to bribe, 
     * - if the player currently holds both treasure and a sun stone, 
     * - the use of sun stone will be prioritised as it will not get used up during the bribing process
     */
    @Test
    public void testBribePriority() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("testBribePriority", "Standard");
        
        // player moves 1 cell to the right to pick up sun stone
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1, true);

        // player moves 1 cell to the right to pick up treasure
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("treasure") == 1, true);

        // mercenary started at (5, 0), now is at (3, 0)
        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(3,0,3), true), controller.getInfo("Mercenary0"));

        // attempt to bribe mercenary with sun stone
        assertDoesNotThrow(() -> {
            controller.interact("Mercenary0");
        });

        // sun stone does not get used up
        assertEquals(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1, true);

        // treasure also does not get used up
        assertEquals(controller.getDungeon().getInventory().numberOfItem("treasure") == 1, true);
    }
    
    /**
     * Test for bribing assassins with:
     * - 1 the one ring
     * - 1 treasure
     */
    @Test
    public void testBribeAssassin() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("testBribeAssassin", "Standard");
        
        // player moves 1 cell to the right to pick up treasure
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("treasure") == 1, true);

        // player moves 1 cell to the right to pick up one ring
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("one_ring") == 1, true);

        // assassin started at (5, 0), now is at (3, 0)
        assertEquals(new EntityResponse("Assassin0", "assassin", new Position(3,0,3), true), controller.getInfo("Assassin0"));

        // attempt to bribe assassin with treasure + one ring
        assertDoesNotThrow(() -> {
            controller.interact("Assassin0");
        });

        // assert both one ring and treasure is used 
        assertEquals(controller.getInfo("TheOneRing0"), null);
        assertEquals(controller.getInfo("Treasure0"), null);
    }

    /**
     * Test for bribing assassins with:
     * - 1 the one ring
     * - 1 sun stone
     */
    @Test
    public void testBribeAssassinSunStone() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("testBribeAssassinSunStone", "Standard");
        
        // player moves 1 cell to the right to pick up sun stone
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1, true);

        // player moves 1 cell to the right to pick up one ring
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("one_ring") == 1, true);

        // assassin started at (5, 0), now is at (3, 0)
        assertEquals(new EntityResponse("Assassin0", "assassin", new Position(3,0,3), true), controller.getInfo("Assassin0"));

        // attempt to bribe assassin with sun stone + one ring
        assertDoesNotThrow(() -> {
            controller.interact("Assassin0");
        });

        // assert one ring is used 
        assertEquals(controller.getInfo("TheOneRing0"), null);

        // sun stone does not get used up
        assertEquals(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1, true);

    }

    /**
     * Test for bribing assassins with:
     * - 1 the one ring
     * - 1 sun stone
     * - 1 treasure
     * As per our assumption:
     * When a player interacts with mercenary/assassin to bribe, 
     * if the player currently holds both treasure and a sun stone, 
     * the use of sun stone will be prioritised as it will not get used up during the bribing process
     */
    @Test
    public void testBribeAssassinPriority() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("testBribeAssassinPriority", "Standard");
        
        // player moves 1 cell to the right to pick up sun stone
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1, true);

        // player moves 1 cell to the right to pick up one ring
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("one_ring") == 1, true);

        // player moves 1 cell to the right to pick up treasure
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("treasure") == 1, true);

        // assassin started at (7, 0), now is at (4, 0)
        assertEquals(new EntityResponse("Assassin0", "assassin", new Position(4,0,3), true), controller.getInfo("Assassin0"));

        // attempt to bribe assassin
        assertDoesNotThrow(() -> {
            controller.interact("Assassin0");
        });

        // assert one ring is used 
        assertEquals(controller.getInfo("TheOneRing0"), null);

        // sun stone does not get used up
        assertEquals(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1, true);

        // treasure also does not get used up
        assertEquals(controller.getDungeon().getInventory().numberOfItem("treasure") == 1, true);
        
    }

    /**
     * Test for bribing assassins with:
     * - 1 the one ring
     * - 1 sun stone
     * - 1 treasure
     * As per our assumption:
     * When a player interacts with mercenary/assassin to bribe, 
     * if the player currently holds both treasure and a sun stone, 
     * the use of sun stone will be prioritised as it will not get used up during the bribing process
     */
    @Test
    public void testBribeAssassinFar() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("testBribeAssassinFar", "Standard");

        // player moves 1 cell to the right to pick up one ring
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("one_ring") == 1, true);

        // player moves 1 cell to the right to pick up treasure
        controller.tick(null, Direction.RIGHT);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("treasure") == 1, true);

        // assassin started at (10, 0), now is at (7, 0)
        assertEquals(new EntityResponse("Assassin0", "assassin", new Position(4,0,3), true), controller.getInfo("Assassin0"));

        // attempt to bribe assassin
        // player is currently at (3, 0), while assassin is at (7, 0)
        assertThrows(InvalidActionException.class, () -> controller.interact("Assassin0"));

        // player moves 1 more tick to the right to get into bribe range of assassin
        controller.tick(null, Direction.RIGHT);

        // player can now bribe assassin
        assertDoesNotThrow(() -> {
            controller.interact("Assassin0");
        });

        // assert both one ring and treasure is used 
        assertEquals(controller.getInfo("TheOneRing0"), null);
        assertEquals(controller.getInfo("Treasure0"), null);
        
    }

}
