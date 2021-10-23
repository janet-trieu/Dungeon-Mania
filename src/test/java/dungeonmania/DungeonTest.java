package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

/**
 * Simple test to check if all possible spawnable entities have loaded
 */
public class DungeonTest {

    /**
     * Test to check if all possible entities on the map are spawned
     * EXCLUDES DROPS AND BUILDABLES: 'Armour', 'TheOneRing', 'Bow', 'Shield'
     * @throws IOException
     */
    @Test
    public void testEntityLoad() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
       
        // LOAD MAP - not sure if this is the correct way... waiting for a response in forums lol
        String path = FileLoader.loadResourceFile("/dungeons/spawnable-entity.json");
        controller.newGame(path, "Peaceful");

        // TEST POSITION OF EACH ENTITY
        // Very ugly and repetitive - might have a change if staff says their eyes are burning
        // Player
        Position position = new Position(0, 0);
        assertEquals(new EntityResponse("Player0", "Player", position, false), controller.getInfo("Player0"));
        // Wall
        position = new Position(1, 0);
        assertEquals(new EntityResponse("Wall0", "Wall", position, false), controller.getInfo("Wall0"));
        // Exit
        position = new Position(2, 0);
        assertEquals(new EntityResponse("Exit0", "Exit", position, false), controller.getInfo("Exit0"));
        // Boulder
        position = new Position(3, 0);
        assertEquals(new EntityResponse("Boulder0", "Boulder", position, false), controller.getInfo("Boulder0"));
        // FloorSwitch
        position = new Position(4, 0);
        assertEquals(new EntityResponse("FloorSwitch0", "FloorSwitch", position, false), controller.getInfo("FloorSwitch0"));
        // Door
        position = new Position(5, 0);
        assertEquals(new EntityResponse("Door0", "Door", position, false), controller.getInfo("Door0"));
        // Portal
        position = new Position(6, 0);
        assertEquals(new EntityResponse("Portal0", "Portal", position, false), controller.getInfo("Portal0"));
        // ZombieToastSpawner
        position = new Position(7, 0);
        assertEquals(new EntityResponse("ZombieToastSpawner0", "ZombieToastSpawner", position, true), controller.getInfo("ZombieToastSpawner0"));
        // Spider
        position = new Position(8, 0);
        assertEquals(new EntityResponse("Spider0", "Spider", position, false), controller.getInfo("Spider0"));
        // ZombieToast
        position = new Position(9, 0);
        assertEquals(new EntityResponse("ZombieToast0", "ZombieToast", position, false), controller.getInfo("ZombieToast0"));
        // Mercenary
        position = new Position(10, 0);
        assertEquals(new EntityResponse("Mercenary0", "Mercenary", position, true), controller.getInfo("Mercenary0"));
        // Treasure
        position = new Position(11, 0);
        assertEquals(new EntityResponse("Treasure0", "Treasure", position, false), controller.getInfo("Treasure0"));
        // Key
        position = new Position(12, 0);
        assertEquals(new EntityResponse("Key0", "Key", position, false), controller.getInfo("Key0"));
        // HealthPotion
        position = new Position(13, 0);
        assertEquals(new EntityResponse("HealthPotion0", "HealthPotion", position, false), controller.getInfo("HealthPotion0"));
        // InvincibilityPotion
        position = new Position(14, 0);
        assertEquals(new EntityResponse("InvincibilityPotion0", "InvincibilityPotion", position, false), controller.getInfo("InvincibilityPotion0"));
        // InvisibilityPotion
        position = new Position(15, 0);
        assertEquals(new EntityResponse("InvisibilityPotion0", "InvisibilityPotion", position, false), controller.getInfo("InvisibilityPotion0"));
        // Wood
        position = new Position(16, 0);
        assertEquals(new EntityResponse("Wood0", "Wood", position, false), controller.getInfo("Wood0"));
        // Arrow
        position = new Position(17, 0);
        assertEquals(new EntityResponse("Arrow0", "Arrow", position, false), controller.getInfo("Arrow0"));
        // Bomb
        position = new Position(18, 0);
        assertEquals(new EntityResponse("Bomb0", "Bomb", position, false), controller.getInfo("Bomb0"));
        // Sword
        position = new Position(19, 0);
        assertEquals(new EntityResponse("Sword0", "Sword", position, false), controller.getInfo("Sword0"));
    }
}
