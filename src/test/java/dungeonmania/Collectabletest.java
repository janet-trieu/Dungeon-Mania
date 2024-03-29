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

        controller.newGame("testBomb", "standard");

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

        controller.newGame("health-potion", "standard");

        controller.tick(null, Direction.RIGHT);
        controller.tick("HealthPotion0", Direction.RIGHT);
    }

    /**
     * Test for player using Health Potion but already full health
     */
    @Test
    public void healBeforeAndAfterBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("health-potion", "standard");

        assertEquals(new EntityResponse("HealthPotion0", "health_potion", new Position (1, 0, 2), false), controller.getInfo("HealthPotion0"));
        // pick up potion
        controller.tick(null, Direction.RIGHT);
        assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(4,0,3), true), controller.getInfo("Mercenary0"));
        // should battle
        controller.tick("HealthPotion0", Direction.NONE);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
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
        controller.newGame("health-potion", "standard");

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
    //     controller.newGame("health-potion", "standard");
    //     assertEquals(new EntityResponse("HealthPotion0", "health_potion", new Position (1, 0, 2), false), controller.getInfo("HealthPotion0"));
    //     assertThrows(InvalidActionException.class, () -> controller.tick("HealthPotion0", Direction.NONE));

    // }

    /**
     * Test for player attempting to use unusable items
     */
    @Test
    public void unusableItem() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bribe", "standard");

        controller.tick(null, Direction.RIGHT);
        
        assertThrows(IllegalArgumentException.class, () -> controller.tick("Treasure0", Direction.RIGHT));
    }

    @Test
    public void testBombCardinallyAdjacent() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBomb2", "standard");

        // player moves 1 cell to the right to pick up bomb
        controller.tick(null, Direction.RIGHT);

        // player moves 1 cell to the right and 1 cell down to push boulder onto switch
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);

        // player moves 1 cell to the left and places a bomb
        controller.tick(null, Direction.LEFT);
        controller.tick("Bomb0", Direction.NONE);

        // assert bomb is at (1,1) and switch is at (2,2) - not cardinally adjacent
        // assert boulder is also at (2,2) where switch is
        // hence, bomb does not explode and the surrounding entities are not destroyed
        Position bombPos = new Position(1, 1, 2);
        Position switchPos = new Position(2, 2, 0);
        Position boulderPos = new Position(2, 2, 1);
        assertEquals(new EntityResponse("Bomb1", "bomb", bombPos, false), controller.getInfo("Bomb1"));
        assertEquals(new EntityResponse("FloorSwitch0", "switch", switchPos, false), controller.getInfo("FloorSwitch0"));
        assertEquals(new EntityResponse("Boulder0", "boulder", boulderPos, false), controller.getInfo("Boulder0"));

        Position wall0Pos = new Position(0, 1, 0);
        Position wall1Pos = new Position(0, 2, 0);
        Position wall2Pos = new Position(1, 2, 0);
        assertEquals(new EntityResponse("Wall0", "wall", wall0Pos, false), controller.getInfo("Wall0"));
        assertEquals(new EntityResponse("Wall1", "wall", wall1Pos, false), controller.getInfo("Wall1"));
        assertEquals(new EntityResponse("Wall2", "wall", wall2Pos, false), controller.getInfo("Wall2"));

    }

}
