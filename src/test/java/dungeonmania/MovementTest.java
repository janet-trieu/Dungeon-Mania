package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.util.*;
import dungeonmania.entities.Player;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Spider;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.entities.staticEntity.Boulder;


/**
 * Simple tests for entities 
 * .move() functions
 */


public class MovementTest {

    /**
     * Testing position once player moves down,
     * and mercenary moves closer to player. 
     * @throws IOException
     */

    @Test
    public void testSimpleMovement() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        Dungeon dungeon = controller.getDungeon();
        Player player = (Player) dungeon.getPlayer();
        Mercenary mercenary = new Mercenary(3,5, dungeon);
        dungeon.addEntity(mercenary);
        player.move(Direction.DOWN);
        mercenary.move();
        
        Position posP = new Position(1,2,4);
        Position posM = new Position(2,5,3);
        
        assertEquals(posP, player.getPosition());
        assertEquals(posM, mercenary.getPosition());

     }
    
    /**
     *  Testing player does not 
     *  walk into wall
     * @throws IOException
     */
    @Test
    public void testPlayerWall() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        Dungeon dungeon = controller.getDungeon();
        Player player = (Player) dungeon.getPlayer();
       
        player.move(Direction.UP);
        Position pos = new Position(1,1,4);

        assertEquals(pos, player.getPosition());
    }

    /**
     * Testing mercenary will move
     * closer to player even if
     * player does not move
     * @throws IOException
     */    
    @Test
    public void testWasteMovement() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        Dungeon dungeon = controller.getDungeon();
        Player player = (Player) dungeon.getPlayer();
        Mercenary mercenary = new Mercenary(3,5, dungeon);
        dungeon.addEntity(mercenary);
       
        player.move(Direction.NONE);
        mercenary.move();

        Position posP = new Position(1,1,4);
        Position posM = new Position(2,5,3);
       
        assertEquals(posP, player.getPosition());
        assertEquals(posM, mercenary.getPosition());
    }

    /**
     * Testing spider will move
     * to cell above on first movement
     * @throws IOException
     */
    @Test
    public void testSpiderMovement() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        Dungeon dungeon = controller.getDungeon();
       
        Spider spider = new Spider(7,13, dungeon);
        dungeon.addEntity(spider);
        Position posS = new Position(7,12,3);

        for(int i = 0; i < 9; i++) {
            spider.move();
        }
        assertEquals(posS, spider.getPosition());
    }
    
    /**
     * Testing if spider will reverse
     * its movement if encounters a boulder
     * @throws IOException
     */
    @Test
    public void testSpiderBoulder() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        Dungeon dungeon = controller.getDungeon();
        

        Spider spider = new Spider(7,13,dungeon);
        dungeon.addEntity(spider);
        Boulder boulder0 = new Boulder(8,12);
        dungeon.addEntity(boulder0);

        spider.move();
        spider.move();
        Position pos = new Position(6,12,3);

        assertEquals(pos, spider.getPosition());
        
    }

    /**
     * Testing if zombie's new position
     * is adjacent to it's original position
     * @throws IOException
     */
    @Test
    public void testZombieToast() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        Dungeon dungeon = controller.getDungeon();
        
        ZombieToast zombie = new ZombieToast(7,13, dungeon);
        dungeon.addEntity(zombie);
        List<Position> possible = new Position(7,13).getAdjacentPositions();
        
        zombie.move();
        Position now = new Position(zombie.getX(), zombie.getY());

        assertTrue(possible.contains(now));
    }

    @Test
    public void testRun() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced-4", "standard");
        Dungeon dungeon = controller.getDungeon();
        Player player = (Player) dungeon.getPlayer();
        Spider spider = new Spider(7,13,dungeon);
        player.move(Direction.RIGHT);
        dungeon.addEntity(spider);
        player.consumeInvincibilityPotion();
        spider.move(); 
        
        assertNotEquals(new Position(7,12,3), spider.getPosition());
    }

    /**
     * Test to further check the "running-away" of entities when player is invincible
     * - also moving entities contact of wall and boulder
     */
    @Test
    public void testRunExtra() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testRunExtra", "standard");

        // player moves one cell to the right to pick up an invincibility potion
        controller.tick(null, Direction.RIGHT);

        // player uses the invincibility potion
        controller.tick("InvincibilityPotion0", Direction.NONE);

        assertEquals(new Position(1, 2, 3), controller.getInfo("Mercenary0").getPosition());
        assertEquals(new Position(4, -1, 3), controller.getInfo("Spider0").getPosition());
        assertEquals(new Position(1, 2, 3), controller.getInfo("Assassin0").getPosition());
        assertEquals(new Position(3, 1, 3), controller.getInfo("ZombieToast0").getPosition());
        assertEquals(new Position(3, 1, 3), controller.getInfo("Hydra0").getPosition());

    }

}

