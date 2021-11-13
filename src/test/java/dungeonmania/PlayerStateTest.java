package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.Armour;
import dungeonmania.entities.collectableEntity.Sword;
import dungeonmania.entities.collectableEntity.potionEntity.InvincibilityPotion;
import dungeonmania.entities.collectableEntity.potionEntity.InvisibilityPotion;
import dungeonmania.entities.collectableEntity.rareCollectableEntity.TheOneRing;
import dungeonmania.entities.movingEntity.Mercenary;

public class PlayerStateTest {
    /**
     * Load states
     */
    @Test
    public void loadStatesTest() {
        Dungeon dungeon = new Dungeon();

        Player player = new Player(0, 0);
        dungeon.addEntity(player);
        assertEquals(false, player.isInvincible());
        assertEquals(false, player.isInvisible());
        assertEquals(false, player.isArmour());
        assertEquals(false, player.isShield());
        assertEquals(false, player.isBow());
        assertEquals(false, player.isSword());
        assertEquals(0, player.getInvincibilityDuration());
        assertEquals(0, player.getInvisibilityDuration());
        assertEquals(0, player.getArmourDurability());
        assertEquals(0, player.getShieldDurability());
        assertEquals(0, player.getBowDurability());
        assertEquals(0, player.getSwordDurability());
        // assertEquals(0, player.getMidnightArmourDurability());

        // player.setPlayerStates(1, 1, 1, 1, 1, 1, 1);
        player.setPlayerStates(1, 1, 1, 1, 1, 1);
        assertEquals(true, player.isInvincible());
        assertEquals(true, player.isInvisible());
        assertEquals(true, player.isArmour());
        assertEquals(true, player.isShield());
        assertEquals(true, player.isBow());
        assertEquals(true, player.isSword());
        assertEquals(true, player.isMidnight());

        assertEquals(1, player.getInvincibilityDuration());
        assertEquals(1, player.getInvisibilityDuration());
        assertEquals(1, player.getArmourDurability());
        assertEquals(1, player.getShieldDurability());
        assertEquals(1, player.getBowDurability());
        assertEquals(1, player.getSwordDurability());
        // assertEquals(1, player.getMidnightArmourDurability());

        // player.setPlayerStates(1, 1, 1, 1, 1, 1, 1);
        player.setPlayerStates(1, 1, 1, 1, 1, 1);
        assertEquals(true, player.isInvincible());
        assertEquals(true, player.isInvisible());
        assertEquals(true, player.isArmour());
        assertEquals(true, player.isShield());
        assertEquals(true, player.isBow());
        assertEquals(true, player.isSword());
        assertEquals(true, player.isMidnight());
    }
    /**
     * Load states on hard gamemode
     */
    @Test
    public void loadStateshardTest() {
        Dungeon dungeon = new Dungeon();

        Player player = new Player(0, 0, "hard");
        dungeon.addEntity(player);
        assertEquals(false, player.isInvincible());
        assertEquals(false, player.isInvisible());
        assertEquals(false, player.isArmour());
        assertEquals(false, player.isShield());
        assertEquals(false, player.isBow());
        assertEquals(false, player.isSword());
        assertEquals(false, player.isMidnight());

        // player.setPlayerStates(1, 1, 1, 1, 1, 1, 1);
        player.setPlayerStates(1, 1, 1, 1, 1, 1);
        assertEquals(false, player.isInvincible());
        assertEquals(true, player.isInvisible());
        assertEquals(true, player.isArmour());
        assertEquals(true, player.isShield());
        assertEquals(true, player.isBow());
        assertEquals(true, player.isSword());
        assertEquals(true, player.isMidnight());

        // player.setPlayerStates(1, 1, 1, 1, 1, 1, 1);
        player.setPlayerStates(1, 1, 1, 1, 1, 1);
        assertEquals(false, player.isInvincible());
        assertEquals(true, player.isInvisible());
        assertEquals(true, player.isArmour());
        assertEquals(true, player.isShield());
        assertEquals(true, player.isBow());
        assertEquals(true, player.isSword());
        assertEquals(true, player.isMidnight());
    }

    /**
     * Test for the duration of invincibility Potion
     */
    @Test
    public void invincibileDurationTest() {
        Dungeon dungeon = new Dungeon();

        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        InvincibilityPotion potion = new InvincibilityPotion(1, 0);
        dungeon.addEntity(potion);

        player.moveRight();

        player.consumeInvincibilityPotion();
        assertEquals(true, player.isInvincible());

        for (int i = 0; i < 10; i++) {
            player.moveRight();
            player.updatePotionDuration();
            assertEquals(10-(i+1), player.getInvincibilityDuration());
        }
        assertEquals(false, player.isInvincible());
    }

    /**
     * Test for the reapplication of invincibility Potion
     */
    @Test
    public void invincibileReapplyTest() {
        Dungeon dungeon = new Dungeon();

        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        InvincibilityPotion potion0 = new InvincibilityPotion(1, 0);
        dungeon.addEntity(potion0);
        player.moveRight();

        InvincibilityPotion potion1 = new InvincibilityPotion(2, 0);
        dungeon.addEntity(potion1);
        player.moveRight();

        player.consumeInvincibilityPotion();
        assertEquals(true, player.isInvincible());

        player.consumeInvincibilityPotion();
        assertEquals(true, player.isInvincible());
    }

    /**
     * Test for the reapplication of invincibility Potion
     */
    @Test
    public void invincibilehardTest() {
        Dungeon dungeon = new Dungeon();

        Player player = new Player(0, 0, "hard");
        dungeon.addEntity(player);

        InvincibilityPotion potion0 = new InvincibilityPotion(1, 0);
        dungeon.addEntity(potion0);
        player.moveRight();

        player.consumeInvincibilityPotion();
        assertEquals(false, player.isInvincible());
    }


     /**
     * Test for the duration of invisibility Potion
     */
    @Test
    public void invisibileDurationTest() {
        Dungeon dungeon = new Dungeon();

        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        InvisibilityPotion potion = new InvisibilityPotion(1, 0);
        dungeon.addEntity(potion);

        player.moveRight();

        player.consumeInvisibilityPotion();
        assertEquals(true, player.isInvisible());

        for (int i = 0; i < 10; i++) {
            player.moveRight();
            player.updatePotionDuration();
            assertEquals(10-(i+1), player.getInvisibilityDuration());
        }
        assertEquals(false, player.isInvisible());
    }

    /**
     * Test for the reapplication of invisibility Potion
     */
    @Test
    public void invisibileReapplyTest() {
        Dungeon dungeon = new Dungeon();

        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        InvisibilityPotion potion0 = new InvisibilityPotion(1, 0);
        dungeon.addEntity(potion0);
        player.moveRight();

        InvisibilityPotion potion1 = new InvisibilityPotion(2, 0);
        dungeon.addEntity(potion1);
        player.moveRight();

        player.consumeInvisibilityPotion();
        assertEquals(true, player.isInvisible());

        player.consumeInvisibilityPotion();
        assertEquals(true, player.isInvisible());
    }

    /**
     * Test for functionality of TheOneRing
     */
    @Test
    public void oneRingTest() {
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        TheOneRing ring = new TheOneRing(1, 0);
        dungeon.addEntity(ring);

        // Create mercenary0 at (2, 0)
        Mercenary mercenary0 = new Mercenary(2, 0, dungeon);
        dungeon.addEntity(mercenary0);
 
        // Create mercenary1 at (3, 0)
        Mercenary mercenary1 = new Mercenary(3, 0, dungeon);
        dungeon.addEntity(mercenary1);      
        // pick up ring 
        player.moveRight();
        assertEquals(false, player.isRing());
        assertEquals(0, player.getOneRingDurability());

        // battle mercenary0
        player.moveRight();
        player.battle(mercenary0);
        assertEquals(true, player.isRing());
        assertEquals(1, player.getOneRingDurability());
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary0));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 6; i++) {
            player.battle(mercenary0);
        }
        // mercenary is dead
        assertEquals(false, dungeon.getEntityList().contains(mercenary0));
        // battle mercenary1
        player.moveRight();
        player.battle(mercenary1);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary1));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 8; i++) {
            player.battle(mercenary1);
        }
        // player battling mercenary0 and mercenary1 consecutively, dies but respawns
        assertEquals(true, dungeon.getEntityList().contains(player));
        assertEquals(false, player.isRing());
        assertEquals(0, player.getOneRingDurability());
        assertEquals(false, inventory.contains(ring));
    }
    @Test
    public void testArmour(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add Armour to inventory
        Armour armour = new Armour(-1, -1);
        inventory.addItem(armour);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);

        Mercenary mercenary1 = new Mercenary(2, 0, dungeon);
        dungeon.addEntity(mercenary1);

        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 5; i++) {
            player.battle(mercenary);
        }
        assertEquals(true, player.isArmour());
        assertEquals(2, player.getArmourDurability());
        // mercenary is not alive after 6 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
        player.moveRight();
        while(player.battle(mercenary1)) {
        }
        assertEquals(false, player.isArmour());
        assertEquals(0, player.getArmourDurability());
    }
    @Test
    public void testSword(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add Armour to inventory
        Sword sword = new Sword(-1, -1);
        inventory.addItem(sword);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0, dungeon);
        dungeon.addEntity(mercenary);

        Mercenary mercenary1 = new Mercenary(2, 0, dungeon);
        dungeon.addEntity(mercenary1);

        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        while (player.battle(mercenary)) {
        }
        assertEquals(true, player.isSword());
        assertEquals(3, player.getSwordDurability());
        // mercenary is not alive after 6 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
        player.moveRight();
        while(player.battle(mercenary1)) {
        }
        assertEquals(false, player.isSword());
        assertEquals(0, player.getSwordDurability());
    }
}
