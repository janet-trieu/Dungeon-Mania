package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
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
        DungeonResponse response0 = controller0.newGame("portals", "Standard");

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
        controller0.newGame("portals", "Standard");

        // TELEPORT AND MAKE SURE POSITION IS CORRECT
        controller0.tick("", Direction.RIGHT);
        Position position = new Position(5, 0, 4);
        assertEquals(new EntityResponse("Player", "player", position, false), controller0.getInfo("Player"));

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
        controller0.newGame("portals", "Standard");
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
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testPersistenceMultipleDifferent() throws IllegalArgumentException, IOException {
        DungeonManiaController controller0 = new DungeonManiaController();

        // CLEAR DATA
        controller0.clearData();

        // LOAD MAP + SAVE
        controller0.newGame("portals", "Standard");
        controller0.saveGame("SaveData0");

        // RESTART, LOAD DIFFERENT MAP AND SAVE
        DungeonManiaController controller1 = new DungeonManiaController();
        controller1.newGame("maze", "Standard");
        controller1.saveGame("SaveData1");

        // THERE SHOULD BE 2 SAVES
        assertEquals(2, controller1.allGames().size());
        // Due to the UNIX timestamp stuff, I can't test names?
    }
}
