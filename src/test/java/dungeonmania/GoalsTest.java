package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import jdk.jfr.Timestamp;

/**
 * Testing for goal functionality:
 *      - ExitGoal
 *      - EnemyGoal
 *      - SwitchGoal
 *      - TreasureGoal
 *      - AndGoal
 *      - OrGoal
 */
public class GoalsTest {
    /**
     * Unit tests for creation of Goals
     */
    
    /**
     * Create a basic exitGoal unit testing
     */
    @Test
    public void testCreateExitGoal() {
        Goal exitGoal = new ExitGoal();
        assertEquals(":exit", exitGoal.toString());
    }

    /**
     * Create a basic enemyGoal unit testing
     */
    @Test
    public void testCreateEnemyGoal() {
        Goal enemyGoal = new enemyGoal();
        assertEquals(":enemy", enemyGoal.toString());
    }

    /**
     * Create a basic switchGoal unit testing
     */
    @Test
    public void testCreateSwitchGoal() {
        Goal switchGoal = new switchGoal();
        assertEquals(":switch", switchGoal.toString());
    }

    /**
     * Create a basic TreasureGoal unit testing
     */
    @Test
    public void testCreateTreasureGoal() {
        Goal treasureGoal = new treasureGoal();
        assertEquals(":treasure", treasureGoal.toString());
    }

    /**
     * Create a basic andGoal 
     */
    @Test
    public void testCreateAndGoal() {
        Goal andGoal = new AndGoal();
        Goal enemyGoal = new EnemyGoal();
        Goal treasureGoal = new TreasureGoal();
        andGoal.addSubGoal(enemyGoal);
        andGoal.addSubGoal(treasureGoal);
        assertEquals("(:enemy AND :treasure)", andGoal.toString());
    }

    /**
     * Create a basic OrGoal
     * 
     */
    @Test
    public void testCreateOrGoal() {
        Goal orGoal = new OrGoal();
        Goal enemyGoal = new EnemyGoal();
        Goal treasureGoal = new TreasureGoal();
        orGoal.addSubGoal(enemyGoal);
        orGoal.addSubGoal(treasureGoal);
        assertEquals("(:enemy OR :treasure)", orGoal.toString());
    }

    /**
     * create an advanced AndGoal
     */
    @Test
    public void testCreateAdvancedAndGoal() {
        Goal goal = new AndGoal();
        Goal andGoal = new AndGoal();
        Goal enemyGoal = new EnemyGoal();
        Goal treasureGoal = new TreasureGoal();
        Goal switchGoal = new SwitchGoal();
        andGoal.addSubGoal(enemyGoal);
        andGoal.addSubGoal(treasureGoal);
        goal.addSubGoal(andGoal);
        goal.addSubGoal(switchGoal);
        assertEquals("((:enemy AND :treasure) AND :switch)", goal.toString());
    }

    /**
     * create an advanced OrGoal
     */
    @Test
    public void testCreateAdvancedOrGoal() {
        Goal goal = new OrGoal();
        Goal orGoal = new OrGoal();
        Goal enemyGoal = new EnemyGoal();
        Goal treasureGoal = new TreasureGoal();
        Goal switchGoal = new SwitchGoal();
        orGoal.addSubGoal(enemyGoal);
        orGoal.addSubGoal(treasureGoal);
        goal.addSubGoal(orGoal);
        goal.addSubGoal(switchGoal);
        assertEquals("((:enemy OR :treasure) Or :switch)", goal.toString());
    }

    /**
     * test for functionality of ExitGoal
     */
    @Test
    public void testExitGoal() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create exit at (1, 0)
        Exit exit = new Exit(1, 0);
        Dungeon.createEntity(exit);
    
        // add ExitGoal to dungeon
        Goal goal = new ExitGoal();
        dungeon.addGoal(goal);
        assertEquals(":exit", goal.toString());

        player.moveRight();

        assertEquals(player.getPosition(), exit.getPosition());

        assertEquals("", goal.toString());
    }

    /**
     * test for functionality of EnemyGoal
     */
    @Test
    public void testEnemyGoal() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0);
        dungeon.createEntity(mercenary);
    
        // add EnemyGoal to dungeon
        Goal goal = new EnemyGoal();
        dungeon.addGoal(goal);
        assertEquals(":enemy", goal.toString());

        // Player should be on same cell as mercenary
        player.moveRight();
        assertEquals(player.getPosition(), mercenary.getPosition());
        // Player battles with mercenary and wins
        player.battle(mercenary);
        assertEquals(false, dungeon.getEntityList().contains(mercenary));

        // Goal is complete
        assertEquals("", goal.toString());
    }

    /**
     * test for functionality of SwitchGoal
     */
    @Test
    public void testSwitchGoal() {
        Dungeon dungeon = new Dungeon();


        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create boulder at (1, 0)
        Boulder boulder = new Boulder(1, 0);
        dungeon.createEntity(boulder);
    
        // Create switch at (2, 0)
        Switch switch0 = new Switch(2, 0);
        dungeon.createEntity(switch0);

        // add SwitchGoal to dungeon
        Goal goal = new SwitchGoal();
        dungeon.addGoal(goal);
        assertEquals(":switch", goal.toString());

        // Player should have moved boulder on switch
        player.moveRight();
        assertEquals(new Position(1, 0, 4), player.getPosition());
        assertEquals(new Position(2, 0, 1), boulder.getPosition());
        assertEquals(switch.getPosition(), boulder.getPosition());

        // Switch should be activated
        assertEquals(true, switch.isActivated());

        // Goal is complete
        assertEquals("", goal.toString());
    }

    /**
     * test for functionality of TreasureGoal
     */
    @Test
    public void testTreasureGoal() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create treasure at (1, 0), (2, 0), & (3, 0)
        Treasure treasure1 = new Treasure(1, 0);
        dungeon.createEntity(treasure1);
        Treasure treasure2 = new Treasure(2, 0);
        dungeon.createEntity(treasure2);
        Treasure treasure3 = new Treasure(3, 0); 
        dungeon.createEntity(treasure3);

        // add TreasureGoal to dungeon
        Goal goal = new TreasureGoal();
        dungeon.addGoal(goal);
        assertEquals(":treasure", goal.toString());

        // Player collects treasure
        player.moveRight();
        assertEquals(new Position(1, 0, 4), player.getPosition());
        inventory.addItem(treasure1);
        assertEquals(false, dungeon.getEntityList().contains(treasure1));

        player.moveRight();
        assertEquals(new Position(2, 0, 4), player.getPosition());
        inventory.addItem(treasure2);
        assertEquals(false, dungeon.getEntityList().contains(treasure2));

        player.moveRight();
        assertEquals(new Position(3, 0, 4), player.getPosition());
        inventory.addItem(treasure3);
        assertEquals(false, dungeon.getEntityList().contains(treasure3));

        // Goal is complete
        assertEquals("", goal.toString());
    }

    /**
     * test for functionality of AndGoal
     */
    @Test
    public void testAndGoal() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create treasure at (1, 0), (2, 0), & (3, 0)
        Treasure treasure1 = new Treasure(1, 0);
        dungeon.createEntity(treasure1);
        Treasure treasure2 = new Treasure(2, 0);
        dungeon.createEntity(treasure2);
        Treasure treasure3 = new Treasure(3, 0); 
        dungeon.createEntity(treasure3);

        // Create exit at (4, 0)
        Exit exit = new Exit(4, 0);
        dungeon.createEntity(exit);

        // add TreasureGoal AND ExitGoal to dungeon
        Goal goal = new AndGoal();
        Goal treasureGoal = new TreasureGoal();
        Goal exitGoal = new ExitGoal();
        goal.addSubGoal(treasureGoal);
        goal.addSubGoal(exitGoal);
        dungeon.addGoal(goal);
        assertEquals(":treasure AND :exit", goal.toString());

        // Player collects treasure
        player.moveRight();
        assertEquals(new Position(1, 0, 4), player.getPosition());
        inventory.addItem(treasure1);
        assertEquals(false, dungeon.getEntityList().contains(treasure1));

        player.moveRight();
        assertEquals(new Position(2, 0, 4), player.getPosition());
        inventory.addItem(treasure2);
        assertEquals(false, dungeon.getEntityList().contains(treasure2));

        player.moveRight();
        assertEquals(new Position(3, 0, 4), player.getPosition());
        inventory.addItem(treasure3);
        assertEquals(false, dungeon.getEntityList().contains(treasure3));

        // TreasureGoal is complete
        assertEquals(":exit", goal.toString());

        // ExitGoal is completed
        player.moveRight();
        assertEquals(player.getPosition() == exit.getPosition());

        assertEquals("", goal.toString());
    }
    /**
     * test for functionality of OrGoal
     */
    @Test
    public void testOrGoalFirst() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create boulder at (1, 0)
        Boulder boulder = new Boulder(1, 0);
        dungeon.createEntity(boulder);
    
        // Create switch at (2, 0)
        Switch switch0 = new Switch(2, 0);
        dungeon.createEntity(switch0);

        // Create mercenary at (0, 1)
        Mercenary mercenary = Mercenary(0, 1);
        dungeon.createEntity(mercenary);

        // add TreasureGoal AND ExitGoal to dungeon
        Goal goal = new OrGoal();
        Goal switchGoal = new SwitchGoal();
        Goal enemyGoal = new EnemyGoal();
        goal.addSubGoal(switchGoal);
        goal.addSubGoal(enemyGoal);
        dungeon.addGoal(goal);
        assertEquals(":switch OR :enemy", goal.toString());

        // Player moves boulder
        // Player should have moved boulder on switch
        player.moveDown();

        // Switch should be activated
        assertEquals(true, switch.isActivated());

        // Goal is complete
        assertEquals("", goal.toString());
    }

    /**
     * test for functionality of OrGoal
     */
    @Test
    public void testOrGoalSecond() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create boulder at (1, 0) and switch at (2, 0)
        Boulder boulder = new Boulder(1, 0);
        dungeon.createEntity(boulder);

        Switch switch0 = new Switch(2, 0);
        dungeon.createEntity(switch0);

        // Create exit at (0, 4)
        Mercenary mercenary = Mercenary(0, 1);
        dungeon.createEntity(mercenary);

        // add TreasureGoal AND ExitGoal to dungeon
        Goal goal = new OrGoal();
        Goal switchGoal = new SwitchGoal();
        Goal enemyGoal = new EnemyGoal();
        goal.addSubGoal(switchGoal);
        goal.addSubGoal(enemyGoal);
        dungeon.addGoal(goal);
        assertEquals(":switch OR :enemy", goal.toString());

        // Player should be on same cell as mercenary
        player.moveDown();
        assertEquals(player.getPosition() == mercenary.getPosition());
        // Player battles with mercenary and wins
        player.battle(mercenary);
        assertEquals(false, dungeon.getEntityList().contains(mercenary));

        // Goal is complete
        assertEquals("", goal.toString());
    }
}
