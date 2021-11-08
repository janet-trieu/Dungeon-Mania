package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;

/**
 * Class to test Buildable Entity
 * P.S this is currently initial stages of testing!!
 */
public class BuildableTest {
    /**
     * Test for building a bow
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuildBow() throws IllegalArgumentException, IOException {
        /**
         * Entities are spawned in:
         * player   (0,0)
         * arrow    (1,0) 
         * arrow    (2,0)
         * arrow    (3,0)
         * wood     (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBow", "Standard");

        // testing if a wrong input is given for a buildable entity
        assertThrows(IllegalArgumentException.class, () -> controller.build("not buildable"));

        // player moves to the right, while picking up the items
        // player will not be able to build a bow unless there are 3 arrows and a wood in the inventory
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        // current inventory = [arrow]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        // current inventory = [arrow, arrow]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        // current inventory = [arrow, arrow, arrow]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [arrow, arrow, arrow, wood]

        // build the bow
        assertDoesNotThrow(() -> {
            controller.build("bow");
        });

        // assert that bow has been added to inventory, 
        // assert that the used collectable entities are removed
        assertEquals(controller.getInfo("Arrow0"), null);
        assertEquals(controller.getInfo("Arrow1"), null);
        assertEquals(controller.getInfo("Arrow2"), null);
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("bow") == 1);
    }

    /**
     * Test for building a shield
     * with key rather than a treasure
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuildShieldWithKey() throws IllegalArgumentException, IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * key          (3,0)
         * treasure     (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testShield", "Standard");

        // testing if a wrong input is given for a buildable entity
        assertThrows(IllegalArgumentException.class, () -> controller.build("not buildable"));

        // player moves to the right, while picking up the items
        // player will not be able to build a shield unless there are 2 woods and a key or a treasure in the inventory
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood]

        controller.tick(null, Direction.RIGHT);

        // current inventory = [wood, wood, key]
        // build the shield
        assertDoesNotThrow(() -> {
            controller.build("shield");
        });

        // assert that shield has been added to inventory, 
        // assert that the used collectable entities are removed
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(controller.getInfo("Wood1"), null);
        assertEquals(controller.getInfo("Key0"), null);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("shield") == 1);
    }

    /**
     * Test for building a shield
     * with treasure instead of key
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuildShieldWithTreasure() throws IllegalArgumentException, IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * treasure     (3,0)
         * key          (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testShield2", "Standard");

        // testing if a wrong input is given for a buildable entity
        assertThrows(IllegalArgumentException.class, () -> controller.build("not buildable"));

        // player moves to the right, while picking up the items
        // player will not be able to build a shield unless there are 2 woods and a key or a treasure in the inventory
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, wood, treasure]

        // build the shield
        assertDoesNotThrow(() -> {
            controller.build("shield");
        });

        // assert that shield has been added to inventory, 
        // assert that the used collectable entities are removed
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(controller.getInfo("Wood1"), null);
        assertEquals(controller.getInfo("Treasure0"), null);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("shield") == 1);
    }

    /**
     * Test for building a shield
     * in the case that the player holds both a key and a treasure
     * the use of treasure is favoured
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuildShieldFavourTreasure() throws IllegalArgumentException, IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * treasure     (3,0)
         * key          (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testShield2", "Standard");

        // testing if a wrong input is given for a buildable entity
        assertThrows(IllegalArgumentException.class, () -> controller.build("not buildable"));

        // player moves to the right, while picking up the items
        // player will not be able to build a shield unless there are 2 woods and a key or a treasure in the inventory
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, wood, treasure]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, wood, treasure, key]

        // build a shield using treasure, not key
        assertDoesNotThrow(() -> {
            controller.build("shield");
        });

        // assert that shield is added to inventory
        // assert that treasure and woods are used and key still remains
        assertEquals(controller.getDungeon().getInventory().numberOfItem("shield") == 1, true);
        assertEquals(controller.getInfo("Treasure0"), null);
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(controller.getInfo("Wood1"), null);
        assertEquals(controller.getDungeon().getInventory().numberOfItem("key") == 1, true);
    }

    /**
     * Test for building a shield
     * - with sun stone
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuildShieldWithSunStone() throws IllegalArgumentException, IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * sun stone    (3,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildShieldSunStone", "Standard");

        // player moves to the right, while picking up the items
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, wood, sun stone]

        // build the shield
        assertDoesNotThrow(() -> {
            controller.build("shield");
        });

        // assert that shield has been added to inventory, 
        // assert that the used collectable entities are removed
        // assert that sun stone still remains
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(controller.getInfo("Wood1"), null);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("shield") == 1);
    }

    /**
     * Test for building a shield
     * - checking to make sure the use of sun stone is prioritised over use of key or treasure when building a shield
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuildShieldPriorityM3() throws IllegalArgumentException, IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * sun stone    (3,0)
         * treasure     (4,0)
         * key          (5,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildShieldPriority", "Standard");

        // player moves to the right, while picking up the items
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        // current inventory = [wood, wood]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, wood, sun stone]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, wood, sun stone, treasure]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, wood, sun stone, treasure, key]

        // build the shield
        assertDoesNotThrow(() -> {
            controller.build("shield");
        });

        // assert that shield has been added to inventory, 
        // assert that the used collectable entities are removed
        // assert that sun stone, key and treasure still remains 
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(controller.getInfo("Wood1"), null);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("treasure") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("key") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("shield") == 1);
    }

}
