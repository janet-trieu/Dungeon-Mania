package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.collectableEntity.potionEntity.InvincibilityPotion;
import dungeonmania.entities.movingEntity.Assassin;
import dungeonmania.entities.movingEntity.Hydra;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BossTest {

    public DungeonManiaController setUp() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bosses", "hard");
        return controller; 
    }

    @Test
    public void testBossesSpawn() {
        DungeonManiaController controller = setUp();
        Dungeon dungeon = controller.getDungeon();
        dungeon.setSpawnPoint(new Position(10, 10));

        for(int i = 0; i < 51; i++) {
            controller.tick(null, Direction.NONE);
        }

        List<MovingEntity> list = dungeon.getMovingEntities();
        assertTrue(list.stream().anyMatch(e -> e instanceof Hydra));
        assertTrue(list.stream().anyMatch(e -> e instanceof Assassin));

    }

   
    
    
}
