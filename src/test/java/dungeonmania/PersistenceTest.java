package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
     */
    @Test
    public void testPersistenceDefault() {
        DungeonManiaController controller0 = new DungeonManiaController();

        // CLEAR SAVE DATA
        controller0.clearData();

        // LOAD MAP (response0)
        DungeonResponse response0 = controller0.newGame("spawnable-entity", "standard");

        // SAVE GAME
        String save0 = "SaveData0";
        controller0.saveGame(save0);

        //RESTART (response1)
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonResponse response1 = controller1.loadGame(save0);

        // COMPARE DATA
        assertEquals(response0.getBuildables(), response1.getBuildables());
        assertEquals(response0.getDungeonName(), response1.getDungeonName());
        assertEquals(response0.getGoals(), response1.getGoals());
        assertEquals(response0.getInventory(), response1.getInventory());
        assertEquals(response0.getDungeonId(), response0.getDungeonId());
        controller1.clearData();
    }

    /**
     * Test if saveGame and loadGame works if something has changed in the map
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testPersistenceModified() throws IOException, InterruptedException {
        DungeonManiaController controller0 = new DungeonManiaController();
        
        // CLEAR SAVE DATA
        controller0.clearData();

        // LOAD MAP
        controller0.newGame("portals", "standard");

        // TELEPORT AND MAKE SURE POSITION IS CORRECT
        controller0.tick(null, Direction.RIGHT);
        Position position = new Position(5, 0, 4);
        assertEquals(new EntityResponse("Player", "player", position, false), controller0.getInfo("Player"));

        // SAVE GAME (response0)
        String save0 = "SaveData0";
        DungeonResponse response0 = controller0.saveGame(save0);

        // RESTART (response1)
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonResponse response1 = controller1.loadGame(save0);

        // COMPARE DATA
        // Due to dungeonId variation that will not be compared
        assertEquals(response0.getBuildables(), response1.getBuildables());
        assertEquals(response0.getDungeonName(), response1.getDungeonName());
        assertEquals(response0.getGoals(), response1.getGoals());
        assertEquals(response0.getInventory(), response1.getInventory());
        controller1.clearData();
    }

    /**
     * Test if saveGame and loadGame works for multiple saves in the same map
     * @throws InterruptedException
     */
    @Test
    public void testPersistenceMultipleSame() throws IOException, InterruptedException {
        DungeonManiaController controller0 = new DungeonManiaController();

        // CLEAR SAVE DATA
        controller0.clearData();

        // LOAD MAP + SAVE
        controller0.newGame("portals", "standard");
        controller0.saveGame("SaveData0");

        // TELPORT AND MOVE + SAVE
        controller0.tick(null, Direction.RIGHT);
        controller0.tick(null, Direction.RIGHT);
        controller0.saveGame("SaveData1");

        // THERE SHOULD BE 2 SAVES NOW
        assertEquals(2, controller0.allGames().size());
        controller0.clearData();
        // Due to the UNIX timestamp stuff, I can't test names?
    }

    /**
     * Test if Inventory is saved and loaded
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws InterruptedException
     */
    @Test
    public void testPersistenceMultipleDifferent() throws IllegalArgumentException, IOException, InterruptedException {
        DungeonManiaController controller0 = new DungeonManiaController();

        // CLEAR DATA
        controller0.clearData();

        // LOAD MAP + SAVE
        controller0.newGame("portals", "standard");
        controller0.saveGame("SaveData0");

        // RESTART, LOAD DIFFERENT MAP AND SAVE
        DungeonManiaController controller1 = new DungeonManiaController();
        controller1.newGame("testBow", "standard");
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.saveGame("SaveData1");

        // THERE SHOULD BE 2 SAVES
        assertEquals(2, controller1.allGames().size());
        controller1.clearData();
    }

}
