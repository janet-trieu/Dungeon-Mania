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
 * Class to test Buildable Entity
 * P.S this is currently initial stages of testing!!
 */
public class BuildableTest {
    /**
     * Test for building a bow
     * @throws IOException
     */
    @Test
    public void testBuildBow() throws IOException {
        /**
         * Entities are spawned in:
         * player   (0,0)
         * arrow    (1,0) 
         * arrow    (2,0)
         * arrow    (3,0)
         * wood     (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/BuildableTest/testBow.json");
        controller.newGame(map, "Standard");

        // testing if a wrong input is given for a buildable entity
        assertThrows(IllegalArgumentException.class, () -> controller.build("not buildable"));

        // player moves to the right, while picking up the items
        // player will not be able to build a bow unless there are 3 arrows and a wood in the inventory
        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        // current inventory = [arrow]

        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        // current inventory = [arrow, arrow]

        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        // current inventory = [arrow, arrow, arrow]

        controller.tick("", Direction.RIGHT);
        // current inventory = [arrow, arrow, arrow, wood]
        assertDoesNotThrow(() -> {
            controller.build("bow");
        });
    }

    /**
     * Test for building a shield
     * with key rather than a treasure
     * @throws IOException
     */
    @Test
    public void testBuildShieldWithKey() throws IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * key          (3,0)
         * treasure     (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/BuildableTest/testShield.json");
        controller.newGame(map, "Standard");

        // testing if a wrong input is given for a buildable entity
        assertThrows(IllegalArgumentException.class, () -> controller.build("not buildable"));

        // player moves to the right, while picking up the items
        // player will not be able to build a shield unless there are 2 woods and a key or a treasure in the inventory
        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood]

        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood]

        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood, key]

        assertDoesNotThrow(() -> {
            controller.build("shield");
        });
    }

    /**
     * Test for building a shield
     * with treasure instead of key
     * @throws IOException
     */
    @Test
    public void testBuildShieldWithTreasure() throws IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * treasure     (3,0)
         * key          (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/BuildableTest/testShield2.json");
        controller.newGame(map, "Standard");

        // testing if a wrong input is given for a buildable entity
        assertThrows(IllegalArgumentException.class, () -> controller.build("not buildable"));

        // player moves to the right, while picking up the items
        // player will not be able to build a shield unless there are 2 woods and a key or a treasure in the inventory
        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood]

        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood]

        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood, treasure]

        assertDoesNotThrow(() -> {
            controller.build("shield");
        });
    }

    /**
     * Test for building a shield
     * in the case that the player holds both a key and a treasure
     * the use of treasure is favoured
     * @throws IOException
     */
    @Test
    public void testBuildShieldFavourTreasure() throws IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * treasure     (3,0)
         * key          (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/BuildableTest/testShield2.json");
        controller.newGame(map, "Standard");

        // testing if a wrong input is given for a buildable entity
        assertThrows(IllegalArgumentException.class, () -> controller.build("not buildable"));

        // player moves to the right, while picking up the items
        // player will not be able to build a shield unless there are 2 woods and a key or a treasure in the inventory
        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood]

        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood]

        controller.tick("", Direction.RIGHT);
        // current inventory = [wood, wood, treasure]

        controller.tick("", Direction.RIGHT);
        // current inventory = [wood, wood, treasure, key]

        // build a bow using treasure, not key
        assertDoesNotThrow(() -> {
            controller.build("shield");
        });

        // assert that treasure is used and key still remains
        assertEquals(controller.getInfo("Treasure0"), null);
        assertEquals(controller.getDungeon().getInventory().contains("key0"), true);
    }

}
