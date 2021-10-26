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
        Spider spider = new Spider(7,13);
        controller.tick("", Direction.DOWN);
        Position posP = new Position(1,0);
        Position posM = new Position(3,4);
        Position posS = new Position(7,12);
    
        EntityResponse mercenary = new EntityResponse("Mercenary0", "mercenary", posM, false);
        EntityResponse s = new EntityResponse("Spider0", "spider", posS, false);
        assertEquals(player.getPosition(), posP);
        assertEquals(mercenary, controller.getInfo("Mercenary0"));
        assertEquals(s, controller.getInfo("Spider0"));

    }
    
}
