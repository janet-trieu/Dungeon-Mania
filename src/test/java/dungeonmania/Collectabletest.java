package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.potionEntity.HealthPotion;
import dungeonmania.entities.collectableEntity.potionEntity.InvincibilityPotion;
import dungeonmania.entities.collectableEntity.potionEntity.InvisibilityPotion;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Class to test Collectable Entity
 * The creation/spawn of collectable entity is tested inside DungeonTest.java
 * Therefore, this file will only test for the application/use of the collectable entities
 * P.S this is currently initial stages of testing!!
 */
public class Collectabletest {
    /**
     * Test for use of health potion
     * @throws IOException
     */
    @Test
    public void testHealthPotion() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // get the inventory
        Inventory inventory = dungeon.getInventory();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // create a mercenary at position (2,0)
        Mercenary mercenary = new Mercenary(2, 0, dungeon);
        dungeon.addEntity(mercenary);

        // create a health potion at position (1,0)
        HealthPotion healthPotion = new HealthPotion(1,0);
        dungeon.addEntity(healthPotion);
        player.moveRight();

        // player moves 1 cell to the right, where it will enter a battle with mercenary (1,0)
        // player will battle 8 ticks (player health = 100, player damage = 10 | mercenary health = 80, mercenary damage = 10)
        player.moveRight();
        for (int i = 0; i < 7; i++) {
            player.battle(mercenary);
        }
        
        // lose some health
        assertEquals(player.getMaxHealth() > player.getHealth(), true);

        // use health potion
        player.consumeHealthPotion();

        // assert that player's health is back to max health
        assertEquals(player.getHealth(), player.getMaxHealth());

        // assert that the health potion is used up
        inventory.removeItem(healthPotion);
        assertEquals(false, inventory.getItems().contains(healthPotion));
    }

    /**
     * Test for the use of invincible potion
     * @throws IOException
     */
    @Test
    public void testInvinciblePotion() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // get the inventory
        Inventory inventory = dungeon.getInventory();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // create an invincible potion at position (1,0)
        InvincibilityPotion invinciblePotion = new InvincibilityPotion(1, 0);
        dungeon.addEntity(invinciblePotion);

        // player moves one cell to the right and picks up the invincible potion
        player.moveRight();

        // player uses the invincible potion
        player.consumeInvincibilityPotion();

        // assert that the player's potion state is invincible
        assertEquals(player.isInvincible(), true);

        // assert that the invincible potion is used up
        assertEquals(false, inventory.getItems().contains(invinciblePotion));
    }

    @Test
    public void testInvincibilityPotionKillEnemy() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // get the inventory
        Inventory inventory = dungeon.getInventory();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // create a mercenary at position (2,0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);

        // create an invincibility potion at position (1,0)
        InvincibilityPotion invinciblePotion = new InvincibilityPotion(1,0);
        dungeon.addEntity(invinciblePotion);

        // player moves 1 cell to the right to pick up invincibility potion
        player.moveRight();

        // player moves 1 cell to the right again to enter a battle with mercenary
        player.moveRight();
        // player uses the invincibility potion 
        player.consumeInvincibilityPotion();
        player.battle(mercenary);


        // assert that mercenary has been killed instantly
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }

    /**
     * Test for the use of invisible potion
     * @throws IOException
     */
    @Test
    public void testInvisiblePotion() {
        // create a dungeon instance
        Dungeon dungeon = new Dungeon();

        // get the inventory
        Inventory inventory = dungeon.getInventory();

        // create a player at position (0,0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // create an invisible potion at position (1,0)
        InvisibilityPotion invisiblePotion = new InvisibilityPotion(1, 0);
        dungeon.addEntity(invisiblePotion);

        // player moves one cell to the right and picks up the invisible potion
        player.moveRight();

        // player uses the invisible potion
        player.consumeInvisibilityPotion();

        // assert that the player's potion state is invisible
        assertEquals(player.isInvisible(), true);

        // assert that the invisible potion is used up
        assertEquals(false, inventory.getItems().contains(invisiblePotion));
    }

    /**
     * Test for the use of bomb
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @Test
    public void testBomb() throws IllegalArgumentException, IOException {
        // create a player at position (0,0)
        // create a bomb at position (1,0)
        // create a wall at position (0,1)
        // create an exit at position (0,2)
        // create a door at position (0,3)
        // create a portal at position (1,3)
        // create a zombie toast spawner at position (2,3)
        // create a switch at position (2,2)
        // createa a boulder at position (2,1)
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("testBomb", "Standard");

        Position exitPosition = new Position(0, 2, 0);
        Position portalPosition = new Position(1, 3, 0);
        Position zombieSpawnerPosition = new Position(2, 3, 0);

        // player moves one cell to the right and picks up the bomb 
        // new position = (1,0)
        controller.tick(null, Direction.RIGHT);

        // player moves two cells down
        // new position = (1,2)
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);

        // player uses/places the bomb
        controller.tick("Bomb0", Direction.NONE);

        // player moves two cells up, one cell to the right
        // new position = (2,0)
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);

        // there is a boulder at position (2,1), which the player will push into the switch with position (2,2)
        controller.tick(null, Direction.DOWN);

        Position newBoulderPosition = new Position(2, 2, 1);
        assertEquals(null, controller.getInfo("Boulder0"));

        // as there is a bomb that is adjacent to the switch that a boulder has been pushed into, the bomb has now exploded
        // thus, ALL entities that are adjacent to the bomb except the player, exit, portal and the zombie toast spawner has now been destroyed.
        assertEquals(controller.getInfo("Wall0"), null);
        assertEquals(controller.getInfo("Switch0"), null);
        assertEquals(controller.getInfo("Boulder0"), null);
        assertEquals(controller.getInfo("Bomb0"), null);

        // checking if player, exit, portal and zombie spawner has been destroyed
        Position newPlayerPosition = new Position(2, 1, 4);
        assertEquals(new EntityResponse("Player", "player", newPlayerPosition, false), controller.getInfo("Player"));
        assertEquals(new EntityResponse("Exit0", "exit", exitPosition, false), controller.getInfo("Exit0"));
        assertEquals(new EntityResponse("Portal0", "portal", portalPosition, false), controller.getInfo("Portal0"));
        assertEquals(new EntityResponse("ZombieToastSpawner0", "zombie_toast_spawner", zombieSpawnerPosition, true), controller.getInfo("ZombieToastSpawner0"));
        assertEquals(new EntityResponse("Door0", "door", new Position(0,3,0), false) ,controller.getInfo("Door0"));
    }
    
    /**
     * Test for player using Health Potion but already full health
     */
    @Test
    public void healthPotionAtFullHealth() {
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("health-potion", "Standard");

        controller.tick(null, Direction.RIGHT);
        controller.tick("HealthPotion0", Direction.RIGHT);
    }

    /**
     * Test for player using Health Potion but already full health
     */
    @Test
    public void healBeforeAndAfterBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("health-potion", "Standard");

        assertEquals(new EntityResponse("HealthPotion0", "health_potion", new Position (1, 0, 2), false), controller.getInfo("HealthPotion0"));
        // pick up potion
        controller.tick(null, Direction.RIGHT);
        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(4,0,3), true), controller.getInfo("Mercenary0"));
        // should battle
        controller.tick("HealthPotion0", Direction.NONE);
        controller.tick(null, Direction.RIGHT);
        assertEquals(null, controller.getInfo("Mercenary0"));
        controller.tick("HealthPotion1", Direction.NONE);
    }

        /**
     * Test for player using Health Potion after battle
     */
    @Test
    public void healAfterBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("health-potion", "Standard");

        assertEquals(new EntityResponse("HealthPotion0", "health_potion", new Position (1, 0, 2), false), controller.getInfo("HealthPotion0"));
        // pick up potion
        controller.tick(null, Direction.RIGHT);
        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(4,0,3), true), controller.getInfo("Mercenary0"));
        // should battle
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        assertEquals(null, controller.getInfo("Mercenary0"));
        controller.tick("HealthPotion1", Direction.NONE);
    }

        /**
     * Test for player using Health Potion after battle
    //  */
    // @Test
    // public void useItemOnFloor() {
    //     DungeonManiaController controller = new DungeonManiaController();
    //     controller.newGame("health-potion", "Standard");
    //     assertEquals(new EntityResponse("HealthPotion0", "health_potion", new Position (1, 0, 2), false), controller.getInfo("HealthPotion0"));
    //     assertThrows(InvalidActionException.class, () -> controller.tick("HealthPotion0", Direction.NONE));

    // }

    /**
     * Test for player attempting to use unusable items
     */
    @Test
    public void unusableItem() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bribe", "Standard");

        controller.tick(null, Direction.RIGHT);
        
        assertThrows(IllegalArgumentException.class, () -> controller.tick("Treasure0", Direction.RIGHT));
    }
}
