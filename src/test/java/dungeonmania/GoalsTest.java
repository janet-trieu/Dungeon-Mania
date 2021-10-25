package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import jdk.jfr.Timestamp;

public class GoalsTest {
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
}
