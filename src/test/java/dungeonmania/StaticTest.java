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
 *      - Portals
 *      - Wall, Exit
 *      - Boulder
 *      - FloorSwitch
 *      - Door
 *      - ZombieToastSpawner
 */
public class StaticTest {

    /**
     * Testing portal functionality
     * @throws IOException
     */
    @Test
    public void testPortals() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();

        String path = FileLoader.loadResourceFile("/dungeons/portals.json");
        controller.newGame(path, "Standard");

        // CASE: TELEPORT RIGHT
        // Player: (0,0) -> (5,0)
        controller.tick("", Direction.RIGHT);
        Position playerPosition= new Position(5, 0);
        assertEquals(new EntityResponse("player0", "player", playerPosition, false), controller.getInfo("player0"));

        // CASE: TELEPORT RIGHT
        // Player: (5,0) -> (0,0)
        controller.tick("", Direction.LEFT);
        playerPosition= new Position(0, 0);
        assertEquals(new EntityResponse("player0", "player", playerPosition, false), controller.getInfo("player0"));

        // MOVING TO SET UP NEXT CASES
        controller.tick("", Direction.RIGHT);
        playerPosition= new Position(5, 0);
        assertEquals(new EntityResponse("player0", "player", playerPosition, false), controller.getInfo("player0"));
        controller.tick("", Direction.DOWN);
        playerPosition = new Position(5, 1);
        assertEquals(new EntityResponse("player0", "player", playerPosition, false), controller.getInfo("player0"));
        controller.tick("", Direction.LEFT);
        playerPosition = new Position(4, 1);
        assertEquals(new EntityResponse("player0", "player", playerPosition, false), controller.getInfo("player0"));

        // CASE: TELEPORT UP
        // Player: (4,1) -> (1, -1)
        controller.tick("", Direction.UP);
        playerPosition = new Position(1, -1);
        assertEquals(new EntityResponse("player0", "player", playerPosition, false), controller.getInfo("player0"));

        // CASE: TELEPORT DOWN
        // Player: (1,-1) -> (4,1)
        controller.tick("", Direction.DOWN);
        playerPosition = new Position(4, 1);
        assertEquals(new EntityResponse("player0", "player", playerPosition, false), controller.getInfo("player0"));
    }

    /**
     * Testing 'Wall' and 'Exit' functionality
     */
    @Test
    public void testMaze() {
        DungeonManiaController controller = new DungeonManiaController();
        
    }
}