package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DungeonTest {

    /**
     * Test to check if all possible entities on the map are spawned
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testEntityLoad() throws IllegalArgumentException, IOException  {
        DungeonManiaController controller = new DungeonManiaController();
       
        controller.newGame("all-entities", "peaceful");

        // TEST POSITION OF EACH ENTITY
        // Player
        Position position = new Position(0, 0, 4);
        assertEquals(new EntityResponse("Player", "player", position, false), controller.getInfo("Player"));
        // Wall
        position = new Position(1, 0);
        assertEquals(new EntityResponse("Wall0", "wall", position, false), controller.getInfo("Wall0"));
        // Exit
        position = new Position(2, 0);
        assertEquals(new EntityResponse("Exit0", "exit", position, false), controller.getInfo("Exit0"));
        // Boulder
        position = new Position(3, 0, 1);
        assertEquals(new EntityResponse("Boulder0", "boulder", position, false), controller.getInfo("Boulder0"));
        // FloorSwitch
        position = new Position(4, 0);
        assertEquals(new EntityResponse("FloorSwitch0", "switch", position, false), controller.getInfo("FloorSwitch0"));
        // DoorClosed
        position = new Position(5, 0);
        assertEquals(new EntityResponse("Door0", "door", position, false), controller.getInfo("Door0"));
        // Portal
        position = new Position(6, 0);
        assertEquals(new EntityResponse("Portal0", "portal", position, false), controller.getInfo("Portal0"));
        // ZombieToastSpawner
        position = new Position(7, 0);
        assertEquals(new EntityResponse("ZombieToastSpawner0", "zombie_toast_spawner", position, true), controller.getInfo("ZombieToastSpawner0"));
        // Spider
        position = new Position(8, 0, 3);
        assertEquals(new EntityResponse("Spider0", "spider", position, false), controller.getInfo("Spider0"));
        // ZombieToast
        position = new Position(9, 0, 3);
        assertEquals(new EntityResponse("ZombieToast0", "zombie_toast", position, false), controller.getInfo("ZombieToast0"));
        // Mercenary
        position = new Position(10, 0, 3);
        assertEquals(new EntityResponse("Mercenary0", "mercenary", position, true), controller.getInfo("Mercenary0"));
        // Treasure
        position = new Position(11, 0, 2);
        assertEquals(new EntityResponse("Treasure0", "treasure", position, false), controller.getInfo("Treasure0"));
        // Key
        position = new Position(12, 0, 2);
        assertEquals(new EntityResponse("Key0", "key", position, false), controller.getInfo("Key0"));
        // HealthPotion
        position = new Position(13, 0, 2);
        assertEquals(new EntityResponse("HealthPotion0", "health_potion", position, false), controller.getInfo("HealthPotion0"));
        // InvincibilityPotion
        position = new Position(14, 0, 2);
        assertEquals(new EntityResponse("InvincibilityPotion0", "invincibility_potion", position, false), controller.getInfo("InvincibilityPotion0"));
        // InvisibilityPotion
        position = new Position(15, 0, 2);
        assertEquals(new EntityResponse("InvisibilityPotion0", "invisibility_potion", position, false), controller.getInfo("InvisibilityPotion0"));
        // Wood
        position = new Position(16, 0, 2);
        assertEquals(new EntityResponse("Wood0", "wood", position, false), controller.getInfo("Wood0"));
        // Arrow
        position = new Position(17, 0, 2);
        assertEquals(new EntityResponse("Arrow0", "arrow", position, false), controller.getInfo("Arrow0"));
        // Bomb
        position = new Position(18, 0, 2);
        assertEquals(new EntityResponse("Bomb0", "bomb", position, false), controller.getInfo("Bomb0"));
        // Sword
        position = new Position(19, 0, 2);
        assertEquals(new EntityResponse("Sword0", "sword", position, false), controller.getInfo("Sword0"));
        // Armour
        position = new Position(20, 0, 2);
        assertEquals(new EntityResponse("Armour0", "armour", position, false), controller.getInfo("Armour0"));
        // Bow
        position = new Position(21, 0, 2);
        assertEquals(new EntityResponse("Bow0", "bow", position, false), controller.getInfo("Bow0"));
        // Shield
        position = new Position(22, 0, 2);
        assertEquals(new EntityResponse("Shield0", "shield", position, false), controller.getInfo("Shield0"));
        // TheOneRing
        position = new Position(23, 0, 2);
        assertEquals(new EntityResponse("TheOneRing0", "the_one_ring", position, false), controller.getInfo("TheOneRing0"));
        // Assassin
        position = new Position(0, 1, 3);
        assertEquals(new EntityResponse("Assassin0", "assassin", position, true), controller.getInfo("Assassin0"));
        // SwampTile
        position = new Position(2, 1, 0);
        assertEquals(new EntityResponse("SwampTile0", "swamp_tile", position, false), controller.getInfo("SwampTile0"));
        // SunStone
        position = new Position(3, 1, 2);
        assertEquals(new EntityResponse("SunStone0", "sun_stone", position, false), controller.getInfo("SunStone0"));
        // Anduril
        position = new Position(4, 1, 2);
        assertEquals(new EntityResponse("Anduril0", "anduril", position, false), controller.getInfo("Anduril0"));
        // Sceptre
        position = new Position(5, 1, 2);
        assertEquals(new EntityResponse("Sceptre0", "sceptre", position, false), controller.getInfo("Sceptre0"));
        // MidnightArmour
        position = new Position(6, 1, 2);
        assertEquals(new EntityResponse("MidnightArmour0", "midnight_armour", position, false), controller.getInfo("MidnightArmour0"));
    }

    /**
     * Test for invalid gamemode input
     */
    @Test
    public void testNewGameInvalidGamemode() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("all-entities", "giveusmarkspls"));
    }

    /**
     * Test for invalid dungeonName input
     */
    @Test
    public void testNewGameInvalidName() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("chickennuggets", "standard"));
    }

    /**
     * Test for invalid name for loadGame
     */
    @Test
    public void testLoadGameInvalidName() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("all-entities", "standard");
        controller.saveGame("pizzashape");
        assertThrows(IllegalArgumentException.class, () -> controller.loadGame("notpizzashape"));
    }

    /**
     * Test for invalid input for itemUsed in tick
     */
    @Test
    public void testTickInvalidItemUsed() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("all-entities", "standard");
        assertThrows(IllegalArgumentException.class, () -> controller.tick("pizzashape", Direction.NONE));
    }

    /**
     * Test for invalid name for build
     */
    @Test
    public void testBuildInvalidBuildable() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBomb", "standard");
        assertThrows(IllegalArgumentException.class, () -> controller.build("the_one_ring"));
    }

    /**
     * Test for invalid entityId
     */
    @Test
    public void testBuildInvalidInteract() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBomb", "standard");
        assertThrows(IllegalArgumentException.class, () -> controller.interact("Chicken0"));
    }
}
