package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

/**
 * Class to test Collectable Entity
 * The creation/spawn of collectable entity is tested inside DungeonTest.java
 * Therefore, this file will only test for the application/use of the collectable entities
 * P.S this is currently initial stages of testing!!
 */
public class CollectableTest {
    /**
     * Test for use of health potion
     * @throws IOException
     */
    @Test
    public void testHealthPotion() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // get the inventory
        Inventory inventory = dungeon.getInventory();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // create a mercenary at position (2,0)
        Mercenary mercenary = new Mercenary(2, 0);
        dungeon.createEntity(mercenary);

        // create a health potion at position (3,0)
        HealthPotion healthPotion = new HealthPotion(3,0);
        dungeon.createEntity(healthPotion);

        // player moves 1 cell to the right, where it will enter a battle with mercenary (1,0)
        // player will battle 8 ticks (player health = 100, player damage = 10 | mercenary health = 80, mercenary damage = 10)
        player.moveRight();
        mercenary.move();
        for (int i = 0; i < 8; i++) {
            player.battle(mercenary);
        }
        
        // lose some health
        assertEquals(player.getHealth(), 20);

        // Player moves 2 cell to the right, where it will collect a health potion
        player.moveRight();
        player.moveRight();
        inventory.addItem(healthPotion);

        // use health potion
        healthPotion.applyEntity();

        // assert that player's health is back to max health
        assertEquals(player.getHealth(), player.getMaxHealth());

        // assert that the health potion is used up
        inventory.removeItem(healthPotion);
        assertEquals(false, inventory.getItems().contains(healthPotion));
    }

    /**
     * Test for the use of invincible potion
     * @throws IOException
     */
    @Test
    public void testInvinciblePotion() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // get the inventory
        Inventory inventory = dungeon.getInventory();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // create an invincible potion at position (1,0)
        InvinciblePotion invinciblePotion = new InvinciblePotion(1, 0);
        dungeon.createEntity(invinciblePotion);

        // player moves one cell to the right and picks up the invincible potion
        player.moveRight();
        inventory.addItem(invinciblePotion);

        // player uses the invincible potion
        invinciblePotion.applyEntity();

        // assert that the player's potion state is invincible
        assertEquals(player.getPotionState().isInvincible(), true);

        // assert that the invincible potion is used up
        inventory.removeItem(invinciblePotion);
        assertEquals(false, inventory.getItems().contains(invinciblePotion));
    }

    /**
     * Test for the use of invisible potion
     * @throws IOException
     */
    @Test
    public void testInvisiblePotion() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // get the inventory
        Inventory inventory = dungeon.getInventory();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // create an invisible potion at position (1,0)
        InvisiblePotion invisiblePotion = new InvisiblePosion(1, 0);
        dungeon.createEntity(invisiblePotion);

        // player moves one cell to the right and picks up the invisible potion
        player.moveRight();
        inventory.addItem(invisiblePotion);

        // player uses the invisible potion
        invisiblePotion.applyEntity();

        // assert that the player's potion state is invisible
        assertEquals(player.getPotionState().isInvisible(), true);

        // assert that the invisible potion is used up
        inventory.removeItem(invisiblePotion);
        assertEquals(false, inventory.getItems().contains(invisiblePotion));
    }

    /**
     * Test for the use of bomb
     * @throws IOException
     */
    @Test
    public void testBomb() {
        // create a player at position (0,0)
        // create a bomb at position (1,0)
        // create a wall at position (0,1)
        // create an exit at position (0,2)
        // create a door at position (0,3)
        // create a portal at position (1,3)
        // create a zombie toast spawner at position (2,3)
        // create a switch at position (2,2)
        // createa a boulder at position (2,1)
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/testCollectableMaps/testBomb.json");
        controller.newGame(map, "Standard");

        Position exitPosition = new Position(0, 2, 0);
        Position portalPosition = new Position(1, 3, 0);
        Position zombieSpawnerPosition = new Position(2, 3, 0);

        // player moves one cell to the right and picks up the bomb 
        // new position = (1,0)
        controller.tick("", Direction.RIGHT);

        // player moves two cells down
        // new position = (1,2)
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);

        // player uses/places the bomb
        controller.tick("Bomb0", Direction.NONE);

        // player moves two cells up, one cell to the right
        // new position = (2,0)
        controller.tick("", Direction.UP);
        controller.tick("", Direction.UP);
        controller.tick("", Direction.RIGHT);

        // there is a boulder at position (2,1), which the player will push into the switch with position (2,2)
        controller.tick("", Direction.DOWN);
        Position newBoulderPosition = new Position(2, 2, 1);
        assertEquals(new EntityResponse("Boulder0", "boulder", newBoulderPosition, false), controller.getInfo("Boulder0"));

        // as there is a bomb that is adjacent to the switch that a boulder has been pushed into, the bomb has now exploded
        // thus, ALL entities that are adjacent to the bomb except the player, exit, portal and the zombie toast spawner has now been destroyed.
        assertEquals(controller.getInfo("Wall0"), null);
        assertEquals(controller.getInfo("Door0"), null);
        assertEquals(controller.getInfo("Switch0"), null);
        assertEquals(controller.getInfo("Boulder0"), null);
        assertEquals(controller.getInfo("Bomb0"), null);

        // checking if player, exit, portal and zombie spawner has been destroyed
        Position newPlayerPosition = new Position(2, 1, 4);
        assertEquals(new EntityResponse("Player", "player", newPlayerPosition, false), controller.getInfo("Player"));
        assertEquals(new EntityResponse("Exit0", "exit", exitPosition, false), controller.getInfo("Exit0"));
        assertEquals(new EntityResponse("Portal0", "portal", portalPosition, false), controller.getInfo("Portal0"));
        assertEquals(new EntityResponse("ZombieToastSpawner0", "zombie_toast_spawner", zombieSpawnerPosition, false), controller.getInfo("ZombieToastSpawner0"));
    }


    /**
     * Test for sword
     * check for the player's damage increase
     * check for the durability decrease when used
     * @throws IOException
     */
    @Test
    public void testSword() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // create a mercenary at position (4,0)
        Mercenary mercenary = new Mercenary(4, 0);
        dungeon.createEntity(mercenary);

        // create a sword at position (1,0)
        Sword sword = new Sword(1,0);
        dungeon.createEntity(sword);

        // player moves one cell to the right to pick up the sword (1,0)
        // by picking up the sword, the player's damage has doubled
        // player's normal damage is set to 10 -- after picking up sword --> 20
        // mercenary has moved to (3,0)
        player.moveRight();
        assertEquals(player.getDamage(), 10);
        dungeon.addInventory(sword);
        assertEquals(player.getDamage(), 20);
        mercenary.move();

        // player moves one cell to the right again, (2,0) where it will enter a battle with mercenary
        player.moveRight();
        mercenary.move();

        // player will battle for 4 times
        player.battle(mercenary);
        player.battle(mercenary);
        player.battle(mercenary);
        player.battle(mercenary);

        // sword will have lost 4 durability since the player has battled 4 times.
        // given that the sword's durability is set to 6, the durability of this sword will be 2.
        assertEquals(dungeon.getInfo(sword.getId()), 2);
    }

    /**
     * Testing for player bribing the mercenary
     * Player HAS treasure
     * @throws IOException
     */
    @Test
    public void testBribeHasTreasure() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // get the inventory
        Inventory inventory = dungeon.getInventory();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // create a mercenary at position (4,0)
        Mercenary mercenary = new Mercenary(4, 0);
        dungeon.createEntity(mercenary);

        // create a treasure at point (1,0)
        Treasure treasure = new Treasure(1, 0);
        dungeon.createEntity(treasure);

        // player moves one cell to the right to pick up the treasure (1,0)
        // mercenary has moved to (3,0)
        player.moveRight();
        inventory.addItem(treasure);
        mercenary.move();

        // player attempts to bribe the mercenary with treasure
        assertDoesNotThrow(() -> {
            player.interact(mercenary.getId());
        });

        // treasure is now used up so it should be removed from inventory
        inventory.removeItem(treasure);
        assertEquals(false, inventory.getItems().contains(treasure));
    }

    /**
     * Testing for player bribing the mercenary
     * Player DOES NOT have treasure
     * @throws InvalidActionException
     */
    @Test
    public void testBribeNoTreasure() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // create a mercenary at position (3,0)
        Mercenary mercenary = new Mercenary(3, 0);
        dungeon.createEntity(mercenary);

        // player moves one cell to the right (1,0)
        player.moveRight();
        // mercenary moves one cell to the left (2,0)
        mercenary.move();

        // player attempts to bribe the mercenary without treasure
        assertThrows(InvalidActionException.class, () -> player.interact(mercenary.getId()));
    }

    /**
     * Test for armour's application
     * check if player isShielded is true
     */
    @Test
    public void testArmour() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // create an armour at position (1,0)
        // this is because we are testing this as unit testing, only wanting to see the application of armour
        // thus, armour is spawned on the floor
        Armour armour = new Armour(1, 0);
        dungeon.createEntity(armour);

        // player moves one cell to the right to pick up armour
        player.moveRight();
        dungeon.addInventory(armour);

        assertEquals(player.getIsShielded(), true);
    }

     /**
     * Test for one ring's application
     */
    @Test
    public void testOneRing() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // create the one ring at position (1,0)
        // this is because we are testing this as unit testing, only wanting to see the application of the one ring
        // thus, the one ring is spawned on the floor
        One_ring one_ring = new One_ring(1, 0);
        dungeon.createEntity(one_ring);

        // player moves one cell to the right to pick up armour
        player.moveRight();
        dungeon.addInventory(one_ring);

        assertEquals(player.getIsRespanwable(), true);
    }

}
