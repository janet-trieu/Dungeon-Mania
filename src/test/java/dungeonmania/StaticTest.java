package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

/**
 * Testing for static entity functionality:
 *      - Portal
 *      - Boulder, FloorSwitch
 *      - Exit, Door (+ Key)
 *      - Wall, ZombieToastSpawner
 */
public class StaticTest {

    /**
     * Testing portal functionality
     * - Player must teleport to corresponding portal and teleport to cardinally adjacent cell
     * @throws IOException
     */
    @Test
    public void testPortals() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/portals.json");
        controller.newGame(map, "Standard");

        // CASE: TELEPORT RIGHT
        // Player: (0,0) -> (5,0)
        controller.tick("", Direction.RIGHT);
        Position playerPosition= new Position(5, 0);
        assertEquals(new EntityResponse("Player0", "Player", playerPosition, false), controller.getInfo("Player0"));

        // CASE: TELEPORT RIGHT
        // Player: (5,0) -> (0,0)
        controller.tick("", Direction.LEFT);
        playerPosition= new Position(0, 0);
        assertEquals(new EntityResponse("Player0", "Player", playerPosition, false), controller.getInfo("Player0"));

        // MOVING TO SET UP NEXT CASES
        controller.tick("", Direction.RIGHT);
        playerPosition= new Position(5, 0);
        assertEquals(new EntityResponse("Player0", "Player", playerPosition, false), controller.getInfo("Player0"));
        controller.tick("", Direction.DOWN);
        playerPosition = new Position(5, 1);
        assertEquals(new EntityResponse("Player0", "Player", playerPosition, false), controller.getInfo("Player0"));
        controller.tick("", Direction.LEFT);
        playerPosition = new Position(4, 1);
        assertEquals(new EntityResponse("Player0", "Player", playerPosition, false), controller.getInfo("Player0"));

        // CASE: TELEPORT UP
        // Player: (4,1) -> (1, -1)
        controller.tick("", Direction.UP);
        playerPosition = new Position(1, -1);
        assertEquals(new EntityResponse("Player0", "Player", playerPosition, false), controller.getInfo("Player0"));

        // CASE: TELEPORT DOWN
        // Player: (1,-1) -> (4,1)
        controller.tick("", Direction.DOWN);
        playerPosition = new Position(4, 1);
        assertEquals(new EntityResponse("Player0", "Player", playerPosition, false), controller.getInfo("Player0"));
    }

    /**
     * Testing boulder and switch functionality
     * - If pushing two Boulder(s), stay put
     * - If Boulder on the same cell as FloorSwitch, it activates it
     * @throws IOException
     */
    @Test
    public void testSwitchBoulder() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/StaticTest/simple-switch-boulder.json");
        controller.newGame(map, "Standard");

        // CASE: PLAYER CANNOT PUSH 2 BOULDER(S) AT ONCE
        controller.tick("", Direction.DOWN);
        // Ensure Player, Boulder(s) are still in place
        Position position = new Position(0, 0);
        assertEquals(new EntityResponse("Player0", "Player", position, false), controller.getInfo("Player0"));
        position = new Position(0, 1);
        assertEquals(new EntityResponse("Boulder0", "Boulder", position, false), controller.getInfo("Boulder0"));
        position = new Position(0, 2);
        assertEquals(new EntityResponse("Boulder1", "Boulder", position, false), controller.getInfo("Boulder1"));

        // CASE: PUSH BOULDER ONTO FLOORSWITCH ACTIVATES IT
        // Push Boulder0 to FloorSwitch0
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.LEFT);
        position = new Position(0, 1);
        assertEquals(new EntityResponse("Player0", "Player", position, false), controller.getInfo("Player0"));
        position = new Position(-1, 1);
        assertEquals(new EntityResponse("Boulder0", "Boulder", position, false), controller.getInfo("Boulder0"));
        // ENSURE FLOORSWITCH0 IS ACTIVATED - not sure how to check this at the moment :(
        
        // Push Boulder1 to FloorSwitch1
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.LEFT);
        controller.tick("", Direction.LEFT);
        controller.tick("", Direction.UP);
        controller.tick("", Direction.RIGHT);
        position = new Position(0, 2);
        assertEquals(new EntityResponse("Player0", "Player", position, false), controller.getInfo("Player0"));
        position = new Position(1, 2);
        assertEquals(new EntityResponse("Boulder1", "Boulder", position, false), controller.getInfo("Boulder0"));
        // ENSURE FLOORSWITCH1 IS ACTIVATED - not sure how to check this at the moment :(
    }

    @Test
    public void testDoorExit() {

    }

}