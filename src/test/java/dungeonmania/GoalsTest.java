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
     * Create a basic treasureGoal unit testing
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
     * Create a basic orGoal
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
     * create an advanced andGoal
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
     * create an advanced andGoal
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
        Player player = dungeon.createEntity(0, 0, "player");

        // Create exit at (1, 0)
        Exit exit = dungeon.createEntity(1, 0, "exit");
    
        // add ExitGoal to dungeon
        Goal goal = new ExitGoal();
        dungeon.addGoal(goal);
        assertEquals(":exit", goal.toString());

        player.moveRight();

        assertEquals(player.getPosition() == exit.getPosition());

        assertEquals("", goal.toString());
    }

    /**
     * test for functionality of EnemyGoal
     */
    @Test
    public void testEnemyGoal() {
        Dungeon dungeon = new Dungeon();


        // Create player at (0, 0)
        Player player = dungeon.createEntity(0, 0, "player");

        // Create mercenary at (1, 0)
        Mercenary mercenary = dungeon.createEntity(1, 0, "mercenary");
    
        // add EnemyGoal to dungeon
        Goal goal = new EnemyGoal();
        dungeon.addGoal(goal);
        assertEquals(":enemy", goal.toString());

        // Player should be on same cell as mercenary
        player.moveRight();
        assertEquals(player.getPosition() == mercenary.getPosition());
        // Player battles with spider and wins
        //player.battle(mercenary);
        assertEquals(null, mercenary);

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
        Player player = dungeon.createEntity(0, 0, "player");

        // Create boulder at (1, 0)
        Boulder boulder = dungeon.createEntity(1, 0, "boulder");
    
        // Create switch at (2, 0)
        Switch switch = dungeon.createEntity(2, 0, "switch");

        // add SwitchGoal to dungeon
        Goal goal = new SwitchGoal();
        dungeon.addGoal(goal);
        assertEquals(":switch", goal.toString());

        // Player should have moved boulder on switch
        player.moveRight();
        assertEquals(new Position(1, 0), player.getPosition());
        assertEquals(new Position(2, 0), boulder.getPosition());
        assertEquals(switch.getPosition(), boulder.getPosition());

        // Switch should be activated
        assertEquals(true, switch.isActivated());

        // Goal is complete
        assertEquals("", goal.toString());
    }

    /**
     * test for functionality of SwitchGoal
     */
    @Test
    public void testTreasureGoal() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = dungeon.createEntity(0, 0, "player");

        // Create treasure at (1, 0), (2, 0), & (3, 0)
        Treasure treasure1 = dungeon.createEntity(1, 0, "treasure");
        Treasure treasure2 = dungeon.createEntity(2, 0, "treasure");
        Treasure treasure3 = dungeon.createEntity(3, 0, "treasure");

        // add TreasureGoal to dungeon
        Goal goal = new TreasureGoal();
        dungeon.addGoal(goal);
        assertEquals(":treasure", goal.toString());

        // Player collects treasure
        player.moveRight();
        assertEquals(new Position(1, 0), player.getPosition());
        assertEquals(null, treasure1);

        player.moveRight();
        assertEquals(new Position(2, 0), player.getPosition());
        assertEquals(null, treasure2);

        player.moveRight();
        assertEquals(new Position(3, 0), player.getPosition());
        assertEquals(null, treasure3);

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
        Player player = dungeon.createEntity(0, 0, "player");

        // Create treasure at (1, 0), (2, 0), & (3, 0)
        Treasure treasure1 = dungeon.createEntity(1, 0, "treasure");
        Treasure treasure2 = dungeon.createEntity(2, 0, "treasure");
        Treasure treasure3 = dungeon.createEntity(3, 0, "treasure");
        // Create exit at (4, 0)
        Exit exit = dungeon.createEntity(4, 0, "exit");

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
        assertEquals(new Position(1, 0), player.getPosition());
        assertEquals(null, treasure1);

        player.moveRight();
        assertEquals(new Position(2, 0), player.getPosition());
        assertEquals(null, treasure2);

        player.moveRight();
        assertEquals(new Position(3, 0), player.getPosition());
        assertEquals(null, treasure3);

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
        Player player = dungeon.createEntity(0, 0, "player");

        // Create boulder at (1, 0) and switch at (2, 0)
        Boulder boulder = dungeon.createEntity(1, 0, "boulder");
        Switch switch = dungeon.createEntity(2, 0, "switch");

        // Create exit at (0, 4)
        Mercenary mercenary = dungeon.createEntity(0, 1, "mercenary");

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
        Player player = dungeon.createEntity(0, 0, "player");

        // Create boulder at (1, 0) and switch at (2, 0)
        Boulder boulder = dungeon.createEntity(1, 0, "boulder");
        Switch switch = dungeon.createEntity(2, 0, "switch");

        // Create exit at (0, 4)
        Mercenary mercenary = dungeon.createEntity(0, 1, "mercenary");

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
        // Player battles with spider and wins
        // player.battle(mercenary);
        assertEquals(null, mercenary);

        // Goal is complete
        assertEquals("", goal.toString());
    }
}
