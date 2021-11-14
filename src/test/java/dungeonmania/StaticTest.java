package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.collectableEntity.SunStone;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.Exit;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.ExitGoal;
import dungeonmania.goals.Goal;
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

        controller.newGame("portals", "standard");

        // CASE: TELEPORT RIGHT
        // Player: (0,0) -> (5,0)
        controller.tick(null, Direction.RIGHT);
        Position playerPosition= new Position(5, 0, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));

        // CASE: TELEPORT RIGHT
        // Player: (5,0) -> (0,0)
        controller.tick(null, Direction.LEFT);
        playerPosition= new Position(0, 0, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));

        // MOVING TO SET UP NEXT CASES
        controller.tick(null, Direction.RIGHT);
        playerPosition= new Position(5, 0, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));
        controller.tick(null, Direction.DOWN);
        playerPosition = new Position(5, 1, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));
        controller.tick(null, Direction.LEFT);
        playerPosition = new Position(4, 1, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));

        // CASE: TELEPORT UP
        // Player: (4,1) -> (1, -1)
        controller.tick(null, Direction.UP);
        playerPosition = new Position(1, -1, 4);
        assertEquals(new EntityResponse("Player", "player", playerPosition, false), controller.getInfo("Player"));

        // CASE: TELEPORT DOWN
        // Player: (1,-1) -> (4,1)
        controller.tick(null, Direction.DOWN);
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
        Goal goal = new ExitGoal(dungeon);
        dungeon.addEntity(player);
        dungeon.addEntity(key);
        dungeon.addEntity(door);
        dungeon.addEntity(exit);
        dungeon.setGoal(goal);
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
        dungeon.updateGoal();
        assertEquals(true, goal.isComplete());
        assertEquals("", goal.toString());
    }

    /**
     * Testing for ZombieToastSpawner and Wall functionality in standard
     * - Wall stays put
     * - ZombieToast spawns every 20 ticks in standard
     */
    
    @Test
    public void teststandardSpawner() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("simple-spawner-wall", "standard");

        // 20 TICKS
        controller.tick(null, Direction.RIGHT);
        for (int i = 0; i <= 19; i++) {
            controller.tick(null, Direction.LEFT);
        }

        // ENSURE WALL AND PLAYER IN PLACE
        Position position = new Position(0, 0, 4);
        assertEquals(new EntityResponse("Player", "player", position, false), controller.getInfo("Player"));
        position = new Position(-1, 0);
        assertEquals(new EntityResponse("Wall0", "wall", position, false), controller.getInfo("Wall0"));

        // ASSERT THERE IS A ZOMBIE TOAST SOMEWHERE
        assertEquals(controller.getDungeon().getInfo("ZombieToast0"), controller.getInfo("ZombieToast0"));

        controller.tick(null, Direction.DOWN);
        position = new Position(1, 1);
        assertEquals(new EntityResponse("ZombieToastSpawner0", "zombie_toast_spawner", position, true), controller.getInfo("ZombieToastSpawner0"));
        
        // INTERACT WITH SPAWNER TO DESTROY IT
        controller.interact("ZombieToastSpawner0");
        // getInfo should return null if it does not exist
        position = new Position(1, 1);
        assertEquals(null, controller.getInfo("ZombieToastSpawner0"));
    }
    
    /**
     * Testing for ZombieToastSpawner and Wall functionality in standard
     * - Wall stays put
     * - ZombieToast spawns every 20 ticks in standard
     */
    @Test
    public void testDestroy() {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("simple-spawner-wall", "standard");

        // 20 TICKS

        controller.tick(null, Direction.RIGHT);
        for (int i = 0; i <= 19; i++) {
            controller.tick(null, Direction.LEFT);
        }

        // ASSERT THERE IS A ZOMBIE TOAST SOMEWHERE
        assertEquals(controller.getDungeon().getInfo("ZombieToast0"), controller.getInfo("ZombieToast0"));

        controller.tick(null, Direction.DOWN);
        
        // INTERACT WITH SPAWNER TO DESTROY IT
        controller.interact("ZombieToastSpawner0");
        // getInfo should return null if it does not exist
        assertEquals(null, controller.getInfo("ZombieToastSpawner0"));
    }
    
    /**
     * Testing for ZombieToastSpawner and Wall functionality in hard
     * - Wall stays put
     * - ZombieToast spawns every 15 ticks in hard
     */
    @Test
    public void testhardSpawner() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("simple-spawner-wall", "hard");

        // 15 TICKS
        for (int i = 0; i <= 15; i++) {
            controller.tick(null, Direction.LEFT);
        }

        // ASSERT THERE IS A ZOMBIE TOAST SOMEWHERE
        assertEquals(controller.getDungeon().getInfo("ZombieToast0"), controller.getInfo("ZombieToast0"));
    }

    @Test
    public void testDestroyNoWeapon() {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("simple-spawner-wall", "standard");

        controller.tick(null, Direction.DOWN);
        
        // INTERACT WITH SPAWNER TO DESTROY IT
        // FAILS NO WEAPON
        assertThrows(InvalidActionException.class, () -> controller.interact("ZombieToastSpawner0"));
        // getInfo should return null if it does not exist
        
        assertEquals(new EntityResponse("ZombieToastSpawner0", "zombie_toast_spawner", new Position (1, 1, 0), true), controller.getInfo("ZombieToastSpawner0"));
    }

    @Test
    public void testInteractIllegalArgument() {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("simple-spawner-wall", "standard");

        controller.tick(null, Direction.DOWN);
        
        // INTERACT WITH SPAWNER TO DESTROY IT
        // FAILS NO WEAPON
        assertThrows(IllegalArgumentException.class, () -> controller.interact("bleh"));
        // getInfo should return null if it does not exist
        
        assertEquals(new EntityResponse("ZombieToastSpawner0", "zombie_toast_spawner", new Position (1, 1, 0), true), controller.getInfo("ZombieToastSpawner0"));
    }

    /**
     * Test for Sun Stone functionality of opening door
     * - doors can be opened with sun stone
     * - sun stone is not used up after door is opened
     */
    @Test
    public void testSunStoneDoor() {

        // create all necessary instances
        Dungeon dungeon = new Dungeon();
        Player player = new Player(0, 0);
        SunStone sunStone = new SunStone(1, 0);
        Door door = new Door(2, 0, 0);

        dungeon.addEntity(player);
        dungeon.addEntity(sunStone);
        dungeon.addEntity(door);

        // player moves one cell to the right to pick up the sun stone
        player.moveRight();
        Position sunStonePos = new Position(1, 0, 4);
        assertEquals(sunStonePos, player.getPosition());
        dungeon.getInventory().addItem(sunStone);
        assertTrue(dungeon.getInventory().getItems().contains(sunStone));

        // player moves one cell to the right to open the door
        player.moveRight();
        Position doorPos = new Position(2, 0, 4);
        assertEquals(doorPos, player.getPosition());
        assertTrue(door.isPassable());
        assertTrue(dungeon.getInventory().getItems().contains(sunStone));

    }
    
    /**
     * Test for door opening with sunstone and key
     * - use of sun stone is favoured over usage of key
     */
    @Test
    public void testSunStoneKeyDoor() {

        // create all necessary instances
        Dungeon dungeon = new Dungeon();
        Player player = new Player(0, 0);
        SunStone sunStone = new SunStone(1, 0);
        Key key = new Key(2, 0, 0);
        Door door = new Door(3, 0, 0);

        dungeon.addEntity(player);
        dungeon.addEntity(sunStone);
        dungeon.addEntity(key);
        dungeon.addEntity(door);

        // player moves one cell to the right to pick up the sun stone
        player.moveRight();
        Position sunStonePos = new Position(1, 0, 4);
        assertEquals(sunStonePos, player.getPosition());
        dungeon.getInventory().addItem(sunStone);
        assertTrue(dungeon.getInventory().getItems().contains(sunStone));

        // player moves one cell to the right to pick up the key
        player.moveRight();
        Position keyPos = new Position(2, 0, 4);
        assertEquals(keyPos, player.getPosition());
        dungeon.getInventory().addItem(key);
        assertTrue(dungeon.getInventory().getItems().contains(key));

        // player moves one cell to the right to open the door
        player.moveRight();
        Position doorPos = new Position(3, 0, 4);
        assertEquals(doorPos, player.getPosition());
        assertTrue(door.isPassable());
        assertTrue(dungeon.getInventory().getItems().contains(sunStone));

        // key is still in inventory
        assertTrue(dungeon.getInventory().getItems().contains(key));

    }

    @Test
    public void doorTest2() {
        Dungeon dungeon = new Dungeon();
        Player player = new Player(0, 0);
        Key key = new Key(1, 0, 0);
        Door door1 = new Door(1, 1, 1);
        Door door2 = new Door(2, 1, 0);

        dungeon.addEntity(player);
        dungeon.addEntity(key);
        dungeon.addEntity(door1);
        dungeon.addEntity(door2);

        // player moves 1 cell to the right to pick up key
        // player moves 1 cell down to try to open door 1
        player.moveRight();
        player.moveDown();

        // door 1 is not the correct door for player's key,
        // hence player cannot move through the door, and stays still
        Position playerPos = new Position(1, 0, 4);
        assertEquals(playerPos, player.getPosition());
        assertFalse(door1.isPassable());

        // player moves 1 cell to the right to pick up key
        // player moves 1 cell down to try to open door 2
        player.moveRight();
        player.moveDown();

        // door 2 is the correct door for player's key, 
        // hence player can move through the door
        assertTrue(door2.isPassable());
        Position door2Pos = new Position(2, 1, 4);
        assertEquals(door2Pos, player.getPosition());
        
    }
    
}