package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.Exit;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.goals.ExitGoal;
import dungeonmania.goals.Goal;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Testing for static entity functionality:
 *      - Portal
 *      - Boulder, FloorSwitch
 *      - Exit, Door (+ Key functionality, + exitGoal functionality)
 *      - Wall, ZombieToastSpawner
 */
public class StaticTest {

    /**
     * Testing portal functionality (system test)
     * - Player must teleport to corresponding portal and teleport to cardinally adjacent cell
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testPortals() throws IllegalArgumentException, IOException {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("portals", "Standard");

        // CASE: TELEPORT RIGHT
        // Player: (0,0) -> (5,0)
        controller.tick("", Direction.RIGHT);
        Position playerPosition= new Position(5, 0, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));

        // CASE: TELEPORT RIGHT
        // Player: (5,0) -> (0,0)
        controller.tick("", Direction.LEFT);
        playerPosition= new Position(0, 0, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));

        // MOVING TO SET UP NEXT CASES
        controller.tick("", Direction.RIGHT);
        playerPosition= new Position(5, 0, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));
        controller.tick("", Direction.DOWN);
        playerPosition = new Position(5, 1, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));
        controller.tick("", Direction.LEFT);
        playerPosition = new Position(4, 1, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));

        // CASE: TELEPORT UP
        // Player: (4,1) -> (1, -1)
        controller.tick("", Direction.UP);
        playerPosition = new Position(1, -1, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));

        // CASE: TELEPORT DOWN
        // Player: (1,-1) -> (4,1)
        controller.tick("", Direction.DOWN);
        playerPosition = new Position(4, 1, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));
    }

    /**
     * Testing boulder and switch functionality
     * - If pushing two Boulder(s), stay put
     * - If Boulder on the same cell as FloorSwitch, it activates it
     * Sorry I know this is ugly - i could split this up but I'm lazy but if you guys hate it I will split it :)
     */
    @Test
    public void testSwitchBoulder() {
        // SPAWN ALL NECESSARY INSTANCES AND ADD TO DUNGEON
        Dungeon dungeon = new Dungeon();
        Player player = new Player(0, 0);
        Boulder boulder0 = new Boulder(0, 1);
        Boulder boulder1 = new Boulder(0, 2);
        FloorSwitch switch0 = new FloorSwitch(-1, 1);
        FloorSwitch switch1 = new FloorSwitch(1, 2);
        dungeon.addEntity(player);
        dungeon.addEntity(boulder0);
        dungeon.addEntity(boulder1);
        dungeon.addEntity(switch0);
        dungeon.addEntity(switch1);

        // CASE: PLAYER CANNOT PUSH 2 BOULDER(S) AT ONCE
        player.moveDown();
        // Ensure Player, Boulder(s) are still in place
        Position position = new Position(0, 0, 4);
        assertEquals(position, player.getPosition());
        position = new Position(0, 1, 1);
        assertEquals(position, boulder0.getPosition());
        position = new Position(0, 2, 1);
        assertEquals(position, boulder1.getPosition());

        // CASE: PUSH BOULDER ONTO FLOORSWITCH ACTIVATES IT
        // Push Boulder0 to FloorSwitch0
        player.moveRight();
        player.moveDown();
        player.moveLeft();
        position = new Position(0, 1, 4);
        assertEquals(position, player.getPosition());
        position = new Position(-1, 1, 1);
        assertEquals(position, boulder0.getPosition());
        assertEquals(true, switch0.getIsActive());
        
        // Push Boulder1 to FloorSwitch1
        player.moveRight();
        player.moveDown();
        player.moveDown();
        player.moveLeft();
        player.moveLeft();
        player.moveUp();
        player.moveRight();
        position = new Position(0, 2, 4);
        assertEquals(position, player.getPosition());
        position = new Position(1, 2, 1);
        assertEquals(position, boulder1.getPosition());
        assertEquals(true, switch1.getIsActive());
    }

    /**
     * Testing Door and Exit functionality
     * - If Door locked, stay put
     * - If Key in Inventory, Door can be opened
     * - If Player same cell as Exit
     */
    @Test
    public void testDoorExit() {
        // SPAWN ALL NECESSARY INSTANCES
        Dungeon dungeon = new Dungeon();
        Player player = new Player(0, 0);
        Key key = new Key(0, 1, 0);
        Door door = new Door(1, 0, 0);
        Exit exit = new Exit(2, 0);
        Goal goal = new ExitGoal();
        dungeon.addEntity(player);
        dungeon.addEntity(key);
        dungeon.addEntity(door);
        dungeon.addEntity(exit);
        dungeon.addGoal(goal);
        assertEquals(false, goal.isComplete());
        assertEquals(":exit", goal.toString());

        // CASE: PLAYER WALKS TO DOORCLOSED WITH NO KEY
        player.moveRight();
        Position position = new Position(0, 0, 4);
        assertEquals(position, player.getPosition());
        position = new Position(1, 0);
        assertEquals(position, door.getPosition());
        assertEquals(false, door.isPassable());

        // PLAYER PICKS UP KEY
        player.moveDown();
        dungeon.getInventory().addItem(key);
        assertEquals(true, dungeon.getInventory().getItems().contains(key));

        // CASE: PLAYER WALKS TO DOORCLOSED WITH KEY
        player.moveUp();
        player.moveRight();
        position = new Position(1, 0, 4);
        assertEquals(position, player.getPosition());
        position = new Position(1, 0);
        assertEquals(position, door.getPosition());
        assertEquals(true, door.isPassable());
        dungeon.getInventory().removeItem(key);
        assertEquals(false, dungeon.getInventory().getItems().contains(key));

        // CASE: PLAYER SAME CELL AS EXIT
        player.moveRight();
        assertEquals(true, goal.isComplete());
        assertEquals("", goal.toString());
    }

    /**
     * Testing for ZombieToastSpawner and Wall functionality in Standard
     * - Wall stays put
     * - ZombieToast spawns every 20 ticks in Standard
     */
    @Test
    public void testStandardSpawner() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("simple-spawner-wall.", "Standard");

        // 20 TICKS
        for (int i = 0; i <= 20; i++) {
            controller.tick("", Direction.LEFT);
        }

        // ENSURE WALL AND PLAYER IN PLACE
        Position position = new Position(0, 0, 4);
        assertEquals(new EntityResponse("Player", "player", position, false), controller.getInfo("Player"));
        position = new Position(-1, 0);
        assertEquals(new EntityResponse("Wall0", "wall", position, false), controller.getInfo("Wall0"));

        // ASSERT THERE IS A ZOMBIE TOAST SOMEWHERE
        assertEquals(controller.getDungeon().getInfo("ZombieToast0"), controller.getInfo("ZombieToast0"));

        controller.tick("", Direction.DOWN);
        position = new Position(1, 1);
        assertEquals(new EntityResponse("ZombieToastSpawner0", "zombie_toast_spawner", position, false), controller.getInfo("ZombieToastSpawner0"));
        
        // INTERACT WITH SPAWNER TO DESTROY IT
        controller.interact("ZombieToastSpawner0");
        // getInfo should return null if it does not exist
        position = new Position(1, 1);
        assertThrows(NullPointerException.class, () -> controller.getInfo("ZombieToastSpawner0"));
    }

    /**
     * Testing for ZombieToastSpawner and Wall functionality in Hard
     * - Wall stays put
     * - ZombieToast spawns every 15 ticks in Hard
     */
    @Test
    public void testHardSpawner() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("simple-spawner-wall", "Hard");

        // 15 TICKS
        for (int i = 0; i <= 15; i++) {
            controller.tick("", Direction.LEFT);
        }

        // ENSURE WALL AND PLAYER IN PLACE
        Position position = new Position(0, 0, 4);
        assertEquals(new EntityResponse("Player", "player", position, false), controller.getInfo("Player"));
        position = new Position(-1, 0);
        assertEquals(new EntityResponse("Wall0", "wall", position, false), controller.getInfo("Wall0"));

        // ASSERT THERE IS A ZOMBIE TOAST SOMEWHERE
        assertEquals(controller.getDungeon().getInfo("ZombieToast0"), controller.getInfo("ZombieToast0"));

        controller.tick("", Direction.DOWN);
        position = new Position(1, 1);
        assertEquals(new EntityResponse("ZombieToastSpawner0", "zombie_toast_spawner", position, false), controller.getInfo("ZombieToastSpawner0"));
        
        // INTERACT WITH SPAWNER TO DESTROY IT
        controller.interact("ZombieToastSpawner0");
        // getInfo should return null if it does not exist
        position = new Position(1, 1);
        assertThrows(NullPointerException.class, () -> controller.getInfo("ZombieToastSpawner0"));
    }
}