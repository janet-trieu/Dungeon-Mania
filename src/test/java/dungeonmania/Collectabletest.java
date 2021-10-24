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
 * Class to test Collectable Entity
 * The creation/spawn of collectable entity is tested inside DungeonTest.java
 * Therefore, this file will only test for the application/use of the collectable entities
 * P.S this is currently initial stages of testing!!
 */
public class Collectabletest {
    /**
     * Test for use of health potion
     * @throws IOException
     */
    @Test
    public void testHealthPotion() {
        DungeonManiaController controller = new DungeonManiaController();
        /**
         * Entities are spawned in:
         * player (0,0)
         * mercenary (2,0)
         * health_potion (3,0)
         */
        String map = FileLoader.loadResourceFile("/dungeons/testCollectableMaps/testHealthPotion.json");
        controller.newGame(map, "Standard");

        /** 
         * Still unsure how implementation will work... 
         * Not sure if newGame "creates" all the entities that are listed in the map.json file
         * Assuming that it DOES NOT;
         */
        // setting player's position to (0,0)
        // assuming there will be a create"Entity" method -> may be createPlayer or createEntity not sure yet..
        // createPlayer(String id, String type, int health, int damage, Position position, String map)
        Position playerPosition = new Position(0, 0);
        Player player1 = createPlayer("player1", "player", 100, 10, playerPosition, map);
        
        // doing similar steps as above for mercenary and health_potion
        // createPlayer(String id, String type, int health, int damage, Position position, String map)
        Position mercenaryPosition = new Position(2,0);
        MovingEntity mercenary1 = createMercenary("mercenary1", "mercenary", 10, 10, mercenaryPosition, map); 
        Position health_potionPosition = new Position(3,0);
        CollectableEntity health_potion1 = createEntity("health_potion1", "health_potion", health_potionPosition, map); 


        // Player moves 1 cell to the right, where it will enter a battle with mercenary (1,0)
        // Player wins after 1 tick, as Player's damage = 10 and Mercenary's health = 10
        controller.tick("", Direction.RIGHT);

        // lose some health
        assertEquals(player1.getHealth(), 90);

        // Player moves 2 cell to the right, where it will collect a health potion
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);

        // use health potion
        controller.tick("health_potion1", Direction.NONE);

        // assert that player's health has been increased
        assertEquals(player1.getHealth(), 100);
    }

    /**
     * Test for the use of invincible potion
     * @throws IOException
     */
    public void testInvinciblePotion() {
        DungeonManiaController controller = new DungeonManiaController();
        /**
         * Entities are spawned in:
         * player (0,0)
         * invincible_potion (1,0)
         */
        String map = FileLoader.loadResourceFile("/dungeons/testCollectableMaps/testInvinciblePotion.json");
        controller.newGame(map, "Standard");

        // create a player at position (0,0)
        Position playerPosition = new Position(0, 0);
        Player player1 = createPlayer("player1", "player", 100, 10, playerPosition, map);

        // create an invicible potion at position (1,0)
        Position invincible_potionPosition = new Position(1,0);
        CollectableEntity invincible_potion1 = createEntity("invincible_potion1", "invincible_potion", invincible_potionPosition, map); 

        // player moves one cell to the right and picks up the invincible potion
        controller.tick("", Direction.RIGHT);

        // player uses the invincible potion
        controller.tick("invincible_potion1", Direction.NONE);

        // assert that the player's potion state is invincible
        assertEquals(player1.getPotionState(), "invincible");
    }

    /**
     * Test for the use of invisible potion
     * @throws IOException
     */
    public void testInvisiblePotion() {
        DungeonManiaController controller = new DungeonManiaController();
        /**
         * Entities are spawned in:
         * player (0,0)
         * invisible_potion (1,0)
         */
        String map = FileLoader.loadResourceFile("/dungeons/testCollectableMaps/testInvisiblePotion.json");
        controller.newGame(map, "Standard");

        // create a player at position (0,0)
        Position playerPosition = new Position(0, 0);
        Player player1 = createPlayer("player1", "player", 100, 10, playerPosition, map);

        // create an invisible potion at position (1,0)
        Position invisible_potionPosition = new Position(1,0);
        CollectableEntity invisible_potion1 = createEntity("invisible_potion1", "invisible_potion", invisible_potionPosition, map); 

        // player moves one cell to the right and picks up the invisible potion
        controller.tick("", Direction.RIGHT);

        // player uses the invisible potion
        controller.tick("invisible_potion1", Direction.NONE);

        // assert that the player's potion state is invisible
        assertEquals(player1.getPotionState(), "invisible");
    }

    /**
     * Test for the use of bomb
     * @throws IOException
     */
    public void testBomb() {
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/testCollectableMaps/testBomb.json");
        controller.newGame(map, "Standard");

        // create a player at position (0,0)
        Position playerPosition = new Position(0, 0);
        Player player1 = createPlayer("player1", "player", 100, 10, playerPosition, map);

        // create a bomb at position (1,0)
        Position bombPosition = new Position(1, 0);
        CollectableEntity bomb1 = createEntity("bomb1", "bomb", bombPosition, map);

        // create a wall at position (0,1)
        Position wallPosition = new Position(0, 1);
        StaticEntity wall1 = createEntity("wall1", "wall", wallPosition, map);

        // create an exit at position (0,2)
        Position exitPosition = new Position(0, 2);
        StaticEntity exit1 = createEntity("exit1", "exit", exitPosition, map);

        // create a door at position (0,3)
        Position doorPosition = new Position(0, 3);
        StaticEntity door1 = createEntity("door1", "door", doorPosition, map);

        // create a portal at position (1,3)
        Position portalPosition = new Position(1, 3);
        StaticEntity portal1 = createEntity("portal1", "portal", portalPosition, map);

        // create a zombie toast spawner at position (2,3)
        Position zombieSpawnerPosition = new Position(2, 3);
        StaticEntity zombieSpawner1 = createEntity("zombieSpawner1", "zombie_spawner", zombieSpawnerPosition, map);

        // create a switch at position (2,2)
        Position switchPosition = new Position(2, 2);
        StaticEntity switch1 = createEntity("switch1", "switch", switchPosition, map);

        // createa a boulder at position (2,1)
        Position boulderPosition = new Position(2, 1);
        StaticEntity boulder = createEntity("boulder1", "boulder", boulderPosition, map);

        // player moves one cell to the right and picks up the bomb 
        // new position = (1,0)
        controller.tick("", Direction.RIGHT);

        // player moves two cells down
        // new position = (1,2)
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);

        // player uses/places the bomb
        controller.tick("bomb1", Direction.NONE);

        // player moves two cells up, one cell to the right
        // new position = (2,0)
        controller.tick("", Direction.UP);
        controller.tick("", Direction.UP);
        controller.tick("", Direction.RIGHT);

        // there is a boulder at position (2,1), which the player will push into the switch with position (2,2)
        controller.tick("", Direction.DOWN);
        Position newBoulderPosition = new Position(2, 2);
        assertEquals(new EntityResponse("boulder1", "boulder", newBoulderPosition, false), controller.getInfo("boulder1"));

        // as there is a bomb that is adjacent to the switch that a boulder has been pushed into, the bomb has now exploded
        // thus, ALL entities that are adjacent to the bomb except the player, exit, portal and the zombie toast spawner has now been destroyed.
        assertEquals(controller.getInfo("wall1"), null);
        assertEquals(controller.getInfo("door1"), null);
        assertEquals(controller.getInfo("switch1"), null);
        assertEquals(controller.getInfo("boulder1"), null);
        assertEquals(controller.getInfo("bomb1"), null);

        // checking if player, exit, portal and zombie spawner has been destroyed
        Position newPlayerPosition = new Position(2, 1);
        assertEquals(new EntityResponse("player1", "player", newPlayerPosition, false), controller.getInfo("player1"));
        assertEquals(new EntityResponse("exit1", "exit", exitPosition, false), controller.getInfo("exit1"));
        assertEquals(new EntityResponse("portal1", "portal", portalPosition, false), controller.getInfo("portal1"));
        assertEquals(new EntityResponse("zombieSpawner1", "zombie_spawner", zombieSpawnerPosition, false), controller.getInfo("zombieSpawner1"));

    }

    /**
     * Test for sword's application
     * @throws IOException
     */
    public void testSwordApplication() {
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/testCollectableMaps/testSwordApplication.json");
        controller.newGame(map, "Standard");

        // create a player at position (0,0)
        Position playerPosition = new Position(0, 0);
        Player player1 = createPlayer("player1", "player", 100, 10, playerPosition, map);

        // create a sword at position (1,0)
        Position swordPosition = new Position(1, 0);
        // createSword("String swordId", int durability, Position position, String map)
        CollectableEntity sword1 = createSword("sword1", 3, swordPosition, map);

        // the sword will double the player's damage
        // the player's current damage is 10, after picking up the sword, it will be 20.
        assertEquals(player1.getDamage(), 10);
        controller.tick("", Direction.RIGHT);
        assertEquals(player1.getDamage(), 20);
    }

    /**
     * Test for sword's durability
     * @throws IOException
     */
    public void testSwordDurability() {
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/testCollectableMaps/testSwordDurability.json");
        controller.newGame(map, "Standard");

        // create a player at position (0,0)
        Position playerPosition = new Position(0, 0);
        Player player1 = createPlayer("player1", "player", 100, 10, playerPosition, map);

        // create a sword at position (1,0)
        Position swordPosition = new Position(1, 0);
        CollectableEntity sword1 = createSword("sword1", 4, swordPosition, map);

        // creates a mercenary at position (4,0)
        Position mercenaryPosition = new Position(4,0);
        MovingEntity mercenary1 = createMercenary("mercenary1", "mercenary", 80, 10, mercenaryPosition, map); 

        // player moves one cell to the right to pick up the sword
        // mercenary has moved to (3,0)
        controller.tick("", Direction.RIGHT);

        // player moves one cell to the right again, (2,0) where it will enter a battle with mercenary
        controller.tick("", Direction.RIGHT);

        // player will battle for 4 times
        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);

        // sword will break as player has battled 4 times and the sword's durability was 4.
        // this means that the player's damage has now returned to its original damage of 10.
        assertEquals(controller.getInfo("sword1"), null);
        assertEquals(player1.getDamage(), 10);
    }

    /**
     * Testing for player bribing the mercenary
     * Player HAS treasure
     * @throws IOException
     */
    public void testBribeHasTreasure() {
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/testCollectableMaps/testSwordDurability.json");
        controller.newGame(map, "Standard");

        // create a player at position (0,0)
        Position playerPosition = new Position(0, 0);
        Player player1 = createPlayer("player1", "player", 100, 10, playerPosition, map);

        // create a treasure at position (1,0)
        Position treasurePosition = new Position(1, 0);
        CollectableEntity treasure1 = createEntity("treasure1", "treasure", treasurePosition, map);

        // creates a mercenary at position (4,0)
        Position mercenaryPosition = new Position(4,0);
        MovingEntity mercenary1 = createMercenary("mercenary1", "mercenary", 80, 10, mercenaryPosition, map); 

        // player moves one cell to the right to pick up the treasure
        // mercenary has moved to (3,0)
        controller.tick("", Direction.RIGHT);

        // player moves one cell to the right again, (2,0) where it will enter the same cell with mercenary
        controller.tick("", Direction.RIGHT);

        // player attempts to bribe the mercenary with treasure
        assertDoesNotThrow(() -> {
            controller.interact("mercenary1");
        });
    }

    /**
     * Testing for player bribing the mercenary
     * Player DOES NOT have treasure
     * @throws IOException
     */
    public void testBribeNoTreasure() {
        DungeonManiaController controller = new DungeonManiaController();

        String map = FileLoader.loadResourceFile("/dungeons/testCollectableMaps/testSwordDurability.json");
        controller.newGame(map, "Standard");

        // create a player at position (0,0)
        Position playerPosition = new Position(0, 0);
        Player player1 = createPlayer("player1", "player", 100, 10, playerPosition, map);


        // creates a mercenary at position (2,0)
        Position mercenaryPosition = new Position(2,0);
        MovingEntity mercenary1 = createMercenary("mercenary1", "mercenary", 80, 10, mercenaryPosition, map); 

        // player moves one cell to the right
        // mercenary has moved to (2,0)
        controller.tick("", Direction.RIGHT);

        // player attempts to bribe the mercenary without treasure
        assertThrows(InvalidActionException.class, () -> controller.interact("mercenary1"));
    }
}
