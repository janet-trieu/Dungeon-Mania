package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

/**
 * Tests for data persistence in saveGame and loadGame
 */
public class PersistenceTest {

    /**
     * Test if saveGame and loadGame works if nothing has changed in the map
     * @throws IOException
     */
    @Test
    public void testPersistenceDefault() throws IOException {
        DungeonManiaController controller0 = new DungeonManiaController();

        // CLEAR SAVE DATA
        controller0.clearData();

        // LOAD MAP (response0)
        String map = FileLoader.loadResourceFile("/dungeons/portals.json");
        DungeonResponse response0 = controller0.newGame(map, "Standard");

        // SAVE GAME
        String save0 = "SaveData0";
        controller0.saveGame(save0);

        // RESTART (response1)
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonResponse response1 = controller1.loadGame(save0);

        // COMPARE DATA
        assertEquals(response0, response1);
    }

    /**
     * Test if saveGame and loadGame works if something has changed in the map
     * @throws IOException
     */
    @Test
    public void testPersistenceModified() throws IOException {
        DungeonManiaController controller0 = new DungeonManiaController();
        
        // CLEAR SAVE DATA
        controller0.clearData();

        // LOAD MAP
        String map = FileLoader.loadResourceFile("/dungeons/portals.json");
        controller0.newGame(map, "Standard");

        // TELEPORT AND MAKE SURE POSITION IS CORRECT
        controller0.tick("", Direction.RIGHT);
        Position position = new Position(5, 0);
        assertEquals(new EntityResponse("Player0", "Player", position, false), controller0.getInfo("Player0"));

        // SAVE GAME (response0)
        String save0 = "SaveData0";
        DungeonResponse response0 = controller0.saveGame(save0);

        // RESTART (response1)
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonResponse response1 = controller1.loadGame(save0);

        // COMPARE DATA
        assertEquals(response0, response1);
    }

    /**
     * Test if saveGame and loadGame works for multiple saves in the same map
     */
    @Test
    public void testPersistenceMultipleSame() throws IOException {
        DungeonManiaController controller0 = new DungeonManiaController();

        // CLEAR SAVE DATA
        controller0.clearData();

        // LOAD MAP + SAVE
        String map = FileLoader.loadResourceFile("/dungeons/portals.json");
        controller0.newGame(map, "Standard");
        controller0.saveGame("SaveData0");

        // TELPORT AND MOVE + SAVE
        controller0.tick("", Direction.RIGHT);
        controller0.tick("", Direction.RIGHT);
        controller0.saveGame("SaveData1");

        // THERE SHOULD BE 2 SAVES NOW
        assertEquals(2, controller0.allGames().size());
        // Due to the UNIX timestamp stuff, I can't test names?
    }

    /**
     * Test if saveGame and loadGame works for multiple saves in different maps
     */
    @Test
    public void testPersistenceMultipleDifferent() {
        DungeonManiaController controller0 = new DungeonManiaController();

        // CLEAR DATA
        controller0.clearData();

        // LOAD MAP + SAVE
        String map = FileLoader.loadResourceFile("/dungeons/portals.json");
        controller0.newGame(map, "Standard");
        controller0.saveGame("SaveData0");

        // RESTART, LOAD DIFFERENT MAP AND SAVE
        DungeonManiaController controller1 = new DungeonManiaController();
        map = FileLoader.loadResourceFile("/dungeons/maze.json");
        controller0.newGame(map, "Standard");
        controller0.saveGame("SaveData1");

        // THERE SHOULD BE 2 SAVES
        assertEquals(2, controller0.allGames().size());
        // Due to the UNIX timestamp stuff, I can't test names?
    }
}
