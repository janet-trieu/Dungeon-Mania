package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.*;

public class MovementTest {

    @Test
    public void testSimpleMovement() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        String map = FileLoader.loadResourceFile("/dungeons/advanced.json");
       
        controller.tick("", Direction.DOWN);
        Position posP = new Position(1,2,4);
        Position posM = new Position(3,4,3);
    
        EntityResponse mercenary = new EntityResponse("Mercenary0", "mercenary", posM, false);
        EntityResponse s = new EntityResponse("Spider0", "spider", posS, false);
        assertEquals(posP, player.getPosition());
        assertEquals(mercenary, controller.getInfo("Mercenary0"));

    }

    @Test
    public void testPlayerWall() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        String map = FileLoader.loadResourceFile("/dungeons/advanced.json");
       
        controller.tick("", Direction.UP);
        Position pos = new Position(1,1,4);

        assertEquals(pos, player.getPosition());
    }

    @Test
    public void testMercMovement() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        String map = FileLoader.loadResourceFile("/dungeons/advanced.json");

        controller.tick("", Direction.NONE);

    }

    @Test
    public void testWasteMovement() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        String map = FileLoader.loadResourceFile("/dungeons/advanced.json");
       
        controller.tick("", Direction.NONE);
        Position posP = new Position(1,1,4);
        Position posM = new Position(3,4,3);
        EntityResponse mercenary = new EntityResponse("Mercenary0", "mercenary", posM, false);

        assertEquals(posP, player.getPosition());
        assertEquals(mercenary, controller.getInfo("Mercenary0"));

    }
    @Test
    public void testSpiderMovement() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        String map = FileLoader.loadResourceFile("/dungeons/advanced.json");

        Spider spider = new Spider(7,13,3);
        createEntity(spider);
        Position posS = new Position(7,12,3);

        for(int i = 0; i < 8; i++) {
            controller.tick("", Direction.NONE);
        }
        assertEquals(posS, spider.getPosition());
    }

    @Test
    public void testSpiderBoulder() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        String map = FileLoader.loadResourceFile("/dungeons/advanced.json");

        Spider spider0 = new Spider(7,13,3);
        createEntity(spider);
        Boulder boulder0 = new Boulder(8,12,1);
        createEntity(boulder);

        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);
        Position pos = new Position(6,12,3);

        assertEquals(pos, spider.getPosition());
        
    }

    @Test
    public void testEnemyAway() throws IOException {
        DungeonManiaController controller = new DungeonManiaController();
        String map = FileLoader.loadResourceFile("/dungeons/advanced.json");
    }
    
}

