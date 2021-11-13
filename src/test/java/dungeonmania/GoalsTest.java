package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.Treasure;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Exit;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.goals.AndGoal;
import dungeonmania.goals.EnemyGoal;
import dungeonmania.goals.ExitGoal;
import dungeonmania.goals.Goal;
import dungeonmania.goals.OrGoal;
import dungeonmania.goals.SwitchGoal;
import dungeonmania.goals.TreasureGoal;
import dungeonmania.util.Position;

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
     * test for functionality of ExitGoal
     */
    @Test
    public void testExitGoal() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create exit at (1, 0)
        Exit exit = new Exit(1, 0);
        dungeon.addEntity(exit);
    
        // add ExitGoal to dungeon
        Goal goal = new ExitGoal(dungeon);
        dungeon.setGoal(goal);
        assertEquals(":exit", goal.toString());

        player.moveRight();

        assertEquals(player.getPosition(), exit.getPosition());

        dungeon.updateGoal();
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
        dungeon.addEntity(player);

        // Create mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);
    
        // add EnemyGoal to dungeon
        Goal goal = new EnemyGoal(dungeon);
        dungeon.setGoal(goal);
        assertEquals(":enemy", goal.toString());

        // Player should be on same cell as mercenary
        player.moveRight();
        assertEquals(player.getPosition(), mercenary.getPosition());
        // Player battles with mercenary and wins
        for (int i = 0; i < 8; i++){
            player.battle(mercenary);
        }
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
        dungeon.updateGoal();
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
        dungeon.addEntity(player);

        // Create boulder at (1, 0)
        Boulder boulder = new Boulder(1, 0);
        dungeon.addEntity(boulder);
    
        // Create switch at (2, 0)
        FloorSwitch switch0 = new FloorSwitch(2, 0);
        dungeon.addEntity(switch0);

        // add SwitchGoal to dungeon
        Goal goal = new SwitchGoal(dungeon);
        dungeon.setGoal(goal);
        assertEquals(":switch", goal.toString());

        // Player should have moved boulder on switch
        player.moveRight();
        assertEquals(new Position(1, 0, 4), player.getPosition());
        assertEquals(new Position(2, 0, 1), boulder.getPosition());
        assertEquals(switch0.getPosition(), boulder.getPosition());

        // Switch should be activated
        assertEquals(true, switch0.getIsActive());

        dungeon.updateGoal();
        // Goal is complete
        assertEquals("", goal.toString());
    }

    /**
     * test for functionality of TreasureGoal
     */
    @Test
    public void testTreasureGoal() {
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create treasure at (1, 0), (2, 0), & (3, 0)
        Treasure treasure1 = new Treasure(1, 0);
        dungeon.addEntity(treasure1);
        Treasure treasure2 = new Treasure(2, 0);
        dungeon.addEntity(treasure2);
        Treasure treasure3 = new Treasure(3, 0); 
        dungeon.addEntity(treasure3);

        // add TreasureGoal to dungeon
        Goal goal = new TreasureGoal(dungeon);
        dungeon.setGoal(goal);
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

        dungeon.updateGoal();
        // Goal is complete
        assertEquals("", goal.toString());
    }

    /**
     * test for functionality of AndGoal
     */
    @Test
    public void testAndGoal() {
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create treasure at (1, 0), (2, 0), & (3, 0)
        Treasure treasure1 = new Treasure(1, 0);
        dungeon.addEntity(treasure1);
        Treasure treasure2 = new Treasure(2, 0);
        dungeon.addEntity(treasure2);
        Treasure treasure3 = new Treasure(3, 0); 
        dungeon.addEntity(treasure3);

        // Create exit at (4, 0)
        Exit exit = new Exit(4, 0);
        dungeon.addEntity(exit);

        // add TreasureGoal AND ExitGoal to dungeon
        AndGoal goal = new AndGoal(dungeon);
        Goal treasureGoal = new TreasureGoal(dungeon);
        Goal exitGoal = new ExitGoal(dungeon);
        goal.addSubGoal(treasureGoal);
        goal.addSubGoal(exitGoal);
        dungeon.setGoal(goal);
        assertEquals("(:treasure AND :exit)", goal.toString());

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

        dungeon.updateGoal();
        // TreasureGoal is complete
        assertEquals(":exit", goal.toString());

        // ExitGoal is completed
        player.moveRight();
        assertEquals(player.getPosition(), exit.getPosition());

        dungeon.updateGoal();
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
        dungeon.addEntity(player);

        // Create boulder at (1, 0)
        Boulder boulder = new Boulder(0, 1);
        dungeon.addEntity(boulder);
    
        // Create switch at (2, 0)
        FloorSwitch switch0 = new FloorSwitch(0, 2);
        dungeon.addEntity(switch0);

        // Create mercenary at (0, 1)
        Mercenary mercenary = new Mercenary(0, 1, dungeon);
        dungeon.addEntity(mercenary);

        // add TreasureGoal AND ExitGoal to dungeon
        OrGoal goal = new OrGoal(dungeon);
        Goal switchGoal = new SwitchGoal(dungeon);
        Goal enemyGoal = new EnemyGoal(dungeon);
        goal.addSubGoal(switchGoal);
        goal.addSubGoal(enemyGoal);
        dungeon.setGoal(goal);
        assertEquals("(:switch OR :enemy)", goal.toString());

        // Player moves boulder
        // Player should have moved boulder on switch
        player.moveDown();
        // Switch should be activated
        assertEquals(true, switch0.getIsActive());

        dungeon.updateGoal();
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
        dungeon.addEntity(player);

        // Create boulder at (0, 1) and switch at (0, 2)
        Boulder boulder = new Boulder(0, 1);
        dungeon.addEntity(boulder);

        FloorSwitch switch0 = new FloorSwitch(0, 2);
        dungeon.addEntity(switch0);

        // Create exit at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);

        // add TreasureGoal AND ExitGoal to dungeon
        OrGoal goal = new OrGoal(dungeon);
        Goal switchGoal = new SwitchGoal(dungeon);
        Goal enemyGoal = new EnemyGoal(dungeon);
        goal.addSubGoal(switchGoal);
        goal.addSubGoal(enemyGoal);
        dungeon.setGoal(goal);
        assertEquals("(:switch OR :enemy)", goal.toString());

        // Player should be on same cell as mercenary
        player.moveRight();
        assertEquals(player.getPosition(), mercenary.getPosition());
        // Player battles with mercenary and wins
        for (int i = 0; i < 8; i++){
            player.battle(mercenary);
        }
        assertEquals(false, dungeon.getEntityList().contains(mercenary));

        // Goal is complete
        dungeon.updateGoal();
        assertEquals("", goal.toString());
    }

    /**
     * test map with enemy goal but no enemies, automatically achieved
     */
    @Test
    public void testNoEnemies() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        Goal enemyGoal = new EnemyGoal(dungeon);

        dungeon.setGoal(enemyGoal);

        assertEquals("", dungeon.getGoalString());
    }

    /**
     * test map with switch goal but no switches, automatically achieved
     */
    @Test
    public void testNoSwitch() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        Goal switchGoal = new SwitchGoal(dungeon);

        dungeon.setGoal(switchGoal);

        assertEquals("", dungeon.getGoalString());
    }

    /**
     * test map with switch goal but no switches, automatically achieved
     */
    @Test
    public void testNoTreasure() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        Goal treasureGoal = new TreasureGoal(dungeon);

        dungeon.setGoal(treasureGoal);

        assertEquals("", dungeon.getGoalString());
    }

}
