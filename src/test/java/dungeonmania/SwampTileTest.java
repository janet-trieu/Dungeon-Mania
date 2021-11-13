package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Assassin;
import dungeonmania.entities.movingEntity.Hydra;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.movingEntity.Spider;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Tests out the functionality of Swamp Tiles and it's effect on entities
 */
public class SwampTileTest {
    
    Position swampPosition = new Position(1, 0);

    public DungeonManiaController setUp() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("swamp-tile", "standard");
        return controller; 
    }

    /**
     * Tests that Player is not affected by Swamp Tiles
     */
    @Test
    public void testPlayerSwamp() {
        DungeonManiaController controller = setUp();

        Entity player = controller.getDungeon().getPlayer();
        // Move player through tile
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        
        // Player should have gone through the tile
        assertEquals(new Position(2, 0, 4), player.getPosition());
    }

    /**
     * Tests that Spider is affected by Swamp Tiles
     */
    @Test
    public void testSpiderSwamp() {
        DungeonManiaController controller = setUp();
        Dungeon dungeon = controller.getDungeon();
        // Add spider in map
        Spider spider = new Spider(1, 1, dungeon);
        dungeon.addEntity(spider);

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        // Ensure spider is still in the same tile as swamptile
        assertEquals(swampPosition, spider.getPosition());
        
        controller.tick(null, Direction.LEFT);

        // Ensure spider is not in the same tile as swamptile (1,0,0)
        assertNotEquals(swampPosition, spider.getPosition());
    }

    /**
     * Tests that Zombie Toast is affected by Swamp Tiles
     */
    @Test
    public void testZombieToastSwamp() {
        DungeonManiaController controller = setUp();
        Dungeon dungeon = controller.getDungeon();
        // Add zombie toast in map (same tile as swamp because zombie movement is random)
        // ASSUMPTION: If entity is spawned on top of Swamp tile, that already counts as 1 tick
        ZombieToast zombie = new ZombieToast(1, 0, dungeon);
        dungeon.addEntity(zombie);

        controller.tick(null, Direction.LEFT);

        // Ensure zombie is still in the same tile as swamptile
        assertEquals(swampPosition, zombie.getPosition());
        
        controller.tick(null, Direction.LEFT);

        // Ensure zombie is not in the same tile as swamptile (1,0,0)
        assertNotEquals(swampPosition, zombie.getPosition());
    }

    /**
     * Tests that Mercenary is affected by Swamp Tiles
     */
    @Test
    public void testMercenarySwamp() {
        DungeonManiaController controller = setUp();
        Dungeon dungeon = controller.getDungeon();
        // Add mercenary in dungeon
        Mercenary mercenary = new Mercenary(2, 0, dungeon);
        dungeon.addEntity(mercenary);

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        // Ensure mercenary is still in the same tile as swamptile
        assertEquals(swampPosition, mercenary.getPosition());
        
        controller.tick(null, Direction.LEFT);

        // Ensure zombie is not in the same tile as swamptile (1,0,0)
        assertNotEquals(swampPosition, mercenary.getPosition());
    }

    /**
     * Tests that Assassin is affected by Swamp Tiles
     */
    @Test
    public void testAssassinSwamp() {
        DungeonManiaController controller = setUp();
        Dungeon dungeon = controller.getDungeon();
        // Add assassin in dungeon
        Assassin assassin = new Assassin(2, 0, dungeon);
        dungeon.addEntity(assassin);

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        // Ensure assassin is still in the same tile as swamptile
        assertEquals(swampPosition, assassin.getPosition());
        
        controller.tick(null, Direction.LEFT);

        // Ensure assassin is not in the same tile as swamptile (1,0,0)
        assertNotEquals(swampPosition, assassin.getPosition());
    }

    /**
     * Tests that Hydra is affected by Swamp Tiles
     */
    @Test
    public void testHydraSwamp() {
        DungeonManiaController controller = setUp();
        Dungeon dungeon = controller.getDungeon();
        // Add hydra in map (same tile as swamp because hydra movement is random)
        // ASSUMPTION: If entity is spawned on top of Swamp tile, that already counts as 1 tick
        Hydra hydra = new Hydra(1, 0, dungeon);
        dungeon.addEntity(hydra);  
        System.out.println(hydra.getPosition());
        
        controller.tick(null, Direction.LEFT);

        // Ensure hydra is still in the same tile as swamptile
        assertEquals(swampPosition, hydra.getPosition());
        
        controller.tick(null, Direction.LEFT);

        // Ensure hydra is not in the same tile as swamptile (1,0,0)
       assertNotEquals(swampPosition, hydra.getPosition());
    }

}
