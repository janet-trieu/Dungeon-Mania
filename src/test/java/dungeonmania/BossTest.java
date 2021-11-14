package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntity.Hydra;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.util.Direction;

public class BossTest {

    public DungeonManiaController setUp() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        return controller; 
    }

    @Test
    public void testHydra() {
        DungeonManiaController controller = setUp();
        Dungeon dungeon = controller.getDungeon();

        for(int i = 0; i < 50; i++) {
            controller.tick(null, Direction.NONE);
        }

        List<MovingEntity> list = dungeon.getMovingEntities();
        assertTrue(list.stream().anyMatch(e -> e instanceof Hydra));
    }

    @Test
    public void testAssassin() {
        DungeonManiaController controller = setUp();
        Dungeon dungeon = controller.getDungeon();

    }
    
}
