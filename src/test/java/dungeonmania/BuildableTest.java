package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;

/**
 * Class to test Buildable Entity and their build implementation
 */
public class BuildableTest {

// ============================================================================================== //
//                              TEST FOR BOW  BUILDING IMPLEMENTATION                             //
// ============================================================================================== //
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

        controller.newGame("testBow", "standard");

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

// ============================================================================================== //
//                             TEST FOR SHIELD  BUILDING IMPLEMENTATION                           //
// ============================================================================================== //

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

        controller.newGame("testShield", "standard");

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

        controller.newGame("testShield2", "standard");

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

        controller.newGame("testShield2", "standard");

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

        controller.newGame("testBuildShieldSunStone", "standard");

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
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(controller.getInfo("Wood1"), null);
        assertEquals(controller.getInfo("SunStone0"), null);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("shield") == 1);
    }

    /**
     * Test for building a shield
     * - checking to make sure the use of sun stone is least favoured over treasure and key
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuildShieldSunStoneTreasure() throws IllegalArgumentException, IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * sun stone    (3,0)
         * treasure     (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildShieldPriority", "standard");

        // player moves to the right, while picking up the items
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("wood") == 1);
        // current inventory = [wood]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("wood") == 2);
        // current inventory = [wood, wood]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, wood, sun stone]
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1);

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, wood, sun stone, treasure]
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("treasure") == 1);

        // build the shield
        assertDoesNotThrow(() -> {
            controller.build("shield");
        });

        // assert that shield has been added to inventory, 
        // assert that the used collectable entities are removed
        // assert that sun stone still remains 
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(controller.getInfo("Wood1"), null);
        assertEquals(controller.getInfo("Treasure0"), null);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("shield") == 1);
    }

    /**
     * Test for building a shield
     * - checking to make sure the use of sun stone is least favoured over treasure and key
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuildShieldSunStoneKey() throws IllegalArgumentException, IOException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * wood         (2,0)
         * sun stone    (3,0)
         * key          (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildShieldPriority2", "standard");

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
        // current inventory = [wood, wood, sun stone, key]

        // build the shield
        assertDoesNotThrow(() -> {
            controller.build("shield");
        });

        // assert that shield has been added to inventory, 
        // assert that the used collectable entities are removed
        // assert that sun stone still remains 
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(controller.getInfo("Wood1"), null);
        assertEquals(controller.getInfo("Key0"), null);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("shield") == 1);
    }

// ============================================================================================== //
//                             TEST FOR SCEPTRE  BUILDING IMPLEMENTATION                          //
// ============================================================================================== //
    /**
     * Test for building a sceptre
     * Can be crafted with: 
     * - one wood or two arrows (two arrows will be prioritised to be used first)
     * - one key or one treasure (use of treasure is prioritised)
     * - one sun stone
     * @throws IllegalArgumentException
     */
    @Test
    public void testBuildSceptre() throws IllegalArgumentException {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * wood         (1,0) 
         * arrow        (2,0)
         * arrow        (3,0)
         * key          (4,0)
         * treasure     (5,0)
         * sun stone    (6,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildSceptre", "standard");

        // player moves to the right, while picking up the items
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [wood]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [wood, arrow]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [wood, arrow, arrow]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [wood, arrow, arrow, key]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [wood, arrow, arrow, key, treasure]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, arrow, arrow, key, treasure, sun_stone]

        // build the sceptre
        assertDoesNotThrow(() -> {
            controller.build("sceptre");
        });

        // assert that sceptre has been added to inventory, 
        // assert that the used collectable entities are removed
        // assert that wood and key still remains 
        assertEquals(controller.getInfo("Arrow0"), null);
        assertEquals(controller.getInfo("Arrow1"), null);
        assertEquals(controller.getInfo("Treasure0"), null);
        assertEquals(controller.getInfo("SunStone0"), null);

        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("wood") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("key") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sceptre") == 1);
    }

    /**
     * Test for building a sceptre
     * with 2 sun stones
     * Can be crafted with: 
     * - Assumption: if the player has 2 sun stones, player does not need a key or treasure
     */
    @Test
    public void testBuildSceptreTwoSunStones() {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * arrow        (1,0) 
         * arrow        (2,0)
         * sun stone    (3,0)
         * sun stone    (4,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildSceptreTwoSunStones", "standard");

        // player moves to the right, while picking up the items
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [arrow]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [arrow, arrow]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [arrow, arrow, sun stone]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [arrow, arrow, sun stone, sun stone]

        // build the sceptre
        assertDoesNotThrow(() -> {
            controller.build("sceptre");
        });

        // assert that sceptre has been added to inventory, 
        // assert that the used collectable entities are removed
        assertEquals(controller.getInfo("Arrow0"), null);
        assertEquals(controller.getInfo("Arrow1"), null);
        assertEquals(controller.getInfo("SunStone0"), null);
        assertEquals(controller.getInfo("SunStone1"), null);

        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sceptre") == 1);

    }

    /**
     * Build Sceptre with: 
     * - wood instead of arrow
     * - key instead of treasure
     */
    @Test
    public void testBuildSceptre1() {

        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildSceptre1", "standard");

        // player moves to the right, while picking up the items
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [wood]

        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
        // current inventory = [wood, key]

        controller.tick(null, Direction.RIGHT);
        // current inventory = [wood, key, sun stone]

        // build the sceptre
        assertDoesNotThrow(() -> {
            controller.build("sceptre");
        });

        // assert that sceptre has been added to inventory, 
        // assert that the used collectable entities are removed
        assertEquals(controller.getInfo("Wood0"), null);
        assertEquals(controller.getInfo("Key0"), null);
        assertEquals(controller.getInfo("SunStone0"), null);

        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sceptre") == 1);

    }

// ============================================================================================== //
//                      TEST FOR MIDNIGHT ARMOUR BUILDING IMPLEMENTATION                          //
// ============================================================================================== //
    /**
     * Test build for midnight armour
     * - with no zombie toasts in the dungeon
     */
    @Test
    public void testBuildMidnightArmour() {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * armour       (1,0)
         * sun stone    (2,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildMidArmour", "standard");

        // player moves to the right, while picking up the items
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("midnight_armour"));

        controller.tick(null, Direction.RIGHT);

        // build midnight armour
        assertDoesNotThrow(() -> {
            controller.build("midnight_armour");
        });

        // assert that the used entities are removed
        // assert that the midnight armour is in inventory
        assertEquals(controller.getInfo("Armour0"), null);
        assertEquals(controller.getInfo("SunStone0"), null);

        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("midnight_armour") == 1);

    }

    /**
     * Test build for midnight armour
     * - with a zombie toasts in the dungeon
     */
    @Test
    public void testBuildMidnightArmourWithZom() {
        /**
         * Entities are spawned in:
         * player       (0,0)
         * armour       (1,0)
         * sun stone    (2,0)
         * zombie toast (7,0)
         */
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildMidArmour2", "standard");

        // player moves to the right, while picking up the items
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("midnight_armour"));

        controller.tick(null, Direction.RIGHT);

        // player has armour and sun stone in inventory
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("armour") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1);

        // build midnight armour
        // but cannot since there is a zombie toast in the dungeon
        assertThrows(InvalidActionException.class, () -> controller.build("midnight_armour"));

        // assert that the used entities are not removed, and 
        // midnight armour is not in inventory
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("armour") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("sun_stone") == 1);
        assertEquals(true, controller.getDungeon().getInventory().numberOfItem("midnight_armour") == 0);

    }

    @Test
    public void MidnightArmourBuildTwoTimes() {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBuildMidArmour3", "standard");

        // player moves to the right, while picking up the items
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        assertTrue(controller.getDungeon().getInventory().numberOfItem("sun_stone") == 2);
        assertTrue(controller.getDungeon().getInventory().numberOfItem("armour") == 2);

        // build midnight armour once
        assertDoesNotThrow(() -> {
            controller.build("midnight_armour");
        });

        // assert that the used entities are removed
        // assert that the midnight armour is in inventory
        assertEquals(controller.getInfo("Armour0"), null);
        assertEquals(controller.getInfo("SunStone0"), null);

        assertTrue(controller.getDungeon().getInventory().numberOfItem("midnight_armour") == 1);

        // build midnight armour twice
        assertDoesNotThrow(() -> {
            controller.build("midnight_armour");
        });

        // assert that the used entities are removed
        // assert that the midnight armour is in inventory
        assertEquals(controller.getInfo("Armour1"), null);
        assertEquals(controller.getInfo("SunStone1"), null);

        assertTrue(controller.getDungeon().getInventory().numberOfItem("midnight_armour") == 2);

    }

}
