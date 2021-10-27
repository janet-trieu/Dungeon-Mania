package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import jdk.jfr.Timestamp;

public class BattleTest {
    @Test
    public void testBattleSpider(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create spider at (1, 0)
        Spider spider = new Spider(1, 0);
        dungeon.createEntity(spider);
        // battle spider
        player.moveRight();
        player.battle(spider);
        // spider is dead
        assertEquals(false, dungeon.getEntityList().contains(spider));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
    }

    @Test
    public void testBattleZombieToast(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create zombieToast at (1, 0)
        ZombieToast zombieToast = new ZombieToast(1, 0);
        dungeon.createEntity(zombieToast);
        // battle ZombieToast
        player.moveRight();
        player.battle(zombieToast);
        // zombieToast is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(zombieToast));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 4; i++) {
            player.battle(zombieToast);
        }
        // zombieToast is not alive
        assertEquals(false, dungeon.getEntityList().contains(zombieToast));
    }
    @Test
    public void testMercenary(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0);
        dungeon.createEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 6; i++) {
            player.battle(mercenary);
        }
        // mercenary is not alive
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }

    @Test
    public void testBow(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create and add bow to inventory
        Bow bow = new Bow(-1, -1);
        inventory.addItem(bow);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0);
        dungeon.createEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 2; i++) {
            player.battle(mercenary);
        }
        // mercenary is not alive after 3 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testSword(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create and add sword to inventory
        Sword sword = new Sword(-1, -1);
        inventory.addItem(sword);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0);
        dungeon.createEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        for (int i = 0; i < 2; i++) {
            player.battle(mercenary);
        }
        // mercenary is not alive after 3 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testShield(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create and add shield to inventory
        Shield shield = new Shield(-1, -1);
        inventory.addItem(shield);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0);
        dungeon.createEntity(mercenary);
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
        // mercenary is not alive after 6 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testArmour(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create and add Armour to inventory
        Armour armour = new Armour(-1, -1);
        inventory.addItem(armour);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0);
        dungeon.createEntity(mercenary);
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
        // mercenary is not alive after 6 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testBowAndSword(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create and add bow to inventory
        Bow bow = new Bow(-1, -1);
        inventory.addItem(bow);

        // Create and add sword to inventory
        Sword sword = new Sword(-1, -1);
        inventory.addItem(sword);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0);
        dungeon.createEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        player.battle(mercenary);
        // mercenary is not alive after 2 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testArmourAndShield(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create and add Armour to inventory
        Armour armour = new Armour(-1, -1);
        inventory.addItem(armour);

        // Create and add shield to inventory
        Shield shield = new Shield(-1, -1);
        inventory.addItem(shield);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0);
        dungeon.createEntity(mercenary);
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
        // mercenary is not alive after 6 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    @Test
    public void testBowAndSwordAndArmourAndShield(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // Create and add bow to inventory
        Bow bow = new Bow(-1, -1);
        inventory.addItem(bow);

        // Create and add sword to inventory
        Sword sword = new Sword(-1, -1);
        inventory.addItem(sword);

        // Create and add Armour to inventory
        Armour armour = new Armour(-1, -1);
        inventory.addItem(armour);

        // Create and add shield to inventory
        Shield shield = new Shield(-1, -1);
        inventory.addItem(shield);

        // Create Mercenary at (1, 0)
        Mercenary mercenary = new Mercenary(1, 0);
        dungeon.createEntity(mercenary);
        // battle Mercenary
        player.moveRight();
        player.battle(mercenary);
        // mercenary is alive after 1 tick
        assertEquals(true, dungeon.getEntityList().contains(mercenary));
        // Player received damage
        assertEquals(true, player.getMaxHealth() > player.getHealth());
        player.battle(mercenary);

        // mercenary is not alive after 1 ticks instead of 7
        assertEquals(false, dungeon.getEntityList().contains(mercenary));
    }
    /*
    @Test
    public void testSimulate() {
        double playerHealth = 10;
        double playerDamage = 1;

        double mercHealth = 10;
        double mercDamage = 1;

        double allyHealth = 0;
        double allyDamage = 0;
        int bow = 3;
        int sword = 6;
        int shield = 5;
        boolean armour = true;


        for (int i = 1; playerHealth > 0 && mercHealth > 0; i++) {
            // armour but no shield
            if (armour == true && shield == 0) {
                playerHealth = playerHealth - (((mercHealth * mercDamage)/10)/2); 
                System.out.println("armour but no shield");
                // shield but no armour
            } else if (armour == false && shield > 0) {
                playerHealth = playerHealth - (((mercHealth * mercDamage)/10)/2); 
                shield--;
                System.out.println("shield but no armour");
                // armour and shield
            } else if (armour == true && shield > 0) {
                playerHealth = playerHealth - (((mercHealth * mercDamage)/10)/4);
                shield--;
                System.out.println("armour and shield");
            } else {
                playerHealth = playerHealth - ((mercHealth * mercDamage)/10);
                System.out.println("no armour or shield");
            }
            // has sword but no bow
            if (sword > 0 && bow == 0) {
                mercHealth = mercHealth - ((playerHealth * playerDamage*2)/5);
                sword --;
                System.out.println("has sword but no bow");
            // has bow but no sword
            } else if (sword == 0 && bow > 0) {
                mercHealth = mercHealth - (((playerHealth * playerDamage)/5)*2);
                bow--;
                System.out.println("has bow but no sword");
            // has sword and bow
            } else if (sword > 0 && bow > 0) {
                mercHealth = mercHealth - (((playerHealth * playerDamage*2)/5)*2);
                bow--;
                sword--;
                System.out.println("has sword and bow");
            }
            // has neither sword or bow
            else {

                mercHealth = mercHealth - ((playerHealth * playerDamage)/5);
                System.out.println("no sword or bow");
            }
            mercHealth = mercHealth - ((allyHealth * allyDamage)/5);
            System.out.println("tick number is: " + i);
            System.out.println("player Health is " + playerHealth);
            System.out.println("merc Health is " + mercHealth);
        }
    }
    */
}
