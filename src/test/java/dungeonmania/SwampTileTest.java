package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Spider;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Tests out the functionality of Swamp Tiles and it's effect on entities
 */
public class SwampTileTest {
    DungeonManiaController controller = new DungeonManiaController();
    Dungeon dungeon = controller.getDungeon();

    Entity player = controller.getDungeon().getPlayer();
    Spider spider = new Spider(1, 1, dungeon);
    Mercenary mercenary = new Mercenary(2, 0, dungeon);
    ZombieToast zombie = new ZombieToast(1, 0, dungeon);
    Assassin assassin = new Assassin(2, 0, dungeon);
    Hydra hydra = new Hydra(1, 0, dungeon);

    Position swampPosition = new Position(1, 0);

    public void setUp() {
        controller.newGame("swamp-tile", "Standard");
    }

    /**
     * Tests that Player is not affected by Swamp Tiles
     */
    @Test
    public void testPlayerSwamp() {
        setUp();

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
        setUp();

        // Add spider in map
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
        setUp();

        // Add zombie toast in map (same tile as swamp because zombie movement is random)
        // ASSUMPTION: If entity is spawned on top of Swamp tile, that already counts as 1 tick
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
        setUp();

        // Add mercenary in dungeon
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
        setUp();

        // Add assassin in dungeon
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
        setUp();

        // Add hydra in map (same tile as swamp because hydra movement is random)
        // ASSUMPTION: If entity is spawned on top of Swamp tile, that already counts as 1 tick
        dungeon.addEntity(hydra);

        controller.tick(null, Direction.LEFT);

        // Ensure hydra is still in the same tile as swamptile
        assertEquals(swampPosition, hydra.getPosition());
        
        controller.tick(null, Direction.LEFT);

        // Ensure hydra is not in the same tile as swamptile (1,0,0)
        assertNotEquals(swampPosition, hydra.getPosition());
    }

}
