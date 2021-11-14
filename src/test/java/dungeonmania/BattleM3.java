package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.breakableEntity.Armour;
import dungeonmania.entities.collectableEntity.breakableEntity.Sword;
import dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity.Bow;
import dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity.Shield;
import dungeonmania.entities.collectableEntity.potionEntity.InvincibilityPotion;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Spider;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

// Testing out battle with the new mobs
// Not finalised as damage and health is not finalised for the mobs
public class BattleM3 {
    @Test
    public void testBattleMercenary() {
        double playerHealth = 10;
        double playerDamage = 1;

        double mercHealth = 30;
        double mercDamage = 5;

        double allyHealth = 0;
        double allyDamage = 0;
        int bow = 3;
        int shield = 5;
        int sword = 6;
        boolean armour = true;


        for (int i = 1; playerHealth > 0 && mercHealth > 0; i++) {
            // armour but no shield
            if (armour == true && shield == 0) {
                playerHealth = playerHealth - (((mercHealth * mercDamage)/10)/2); 
                // shield but no armour
            } else if (armour == false && shield > 0) {
                playerHealth = playerHealth - (((mercHealth * mercDamage)/10)/2); 
                shield--;
                // armour and shield
            } else if (armour == true && shield > 0) {
                playerHealth = playerHealth - (((mercHealth * mercDamage)/10)/4);
                shield--;
            } else {
                playerHealth = playerHealth - ((mercHealth * mercDamage)/10);
            }
            // has sword but no bow
            if (sword > 0 && bow == 0) {
                mercHealth = mercHealth - ((playerHealth * playerDamage*2)/5);
                sword --;
            // has bow but no sword
            } else if (sword == 0 && bow > 0) {
                mercHealth = mercHealth - (((playerHealth * playerDamage)/5)*2);
                bow--;
            // has sword and bow
            } else if (sword > 0 && bow > 0) {
                mercHealth = mercHealth - (((playerHealth * playerDamage*2)/5)*2);
                bow--;
                sword--;
            }
            // has neither sword or bow
            else {
                mercHealth = mercHealth - ((playerHealth * playerDamage)/5);
            }
            mercHealth = mercHealth - ((allyHealth * allyDamage)/5);
            System.out.println("tick number is: " + i);
            System.out.println("player Health is " + playerHealth);
            System.out.println("merc Health is " + mercHealth);
        }
    }
   
    // battle assassin with nothing
    @Test
    public void assassinBattle() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create Assassin at (1, 0)
        Assassin assassin = new Assassin(1, 0, dungeon);
        dungeon.addEntity(assassin);
        // battle assassin
        player.moveRight();
        player.battle(assassin);
        // player is dead
        assertFalse(dungeon.getEntityList().contains(player));
    }
    // battle assassin with sword, bow, shield and armour
    @Test
    public void assassinBattleEquipped() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        Bow bow = new Bow(1, 0);
        dungeon.addEntity(bow);

        Shield shield = new Shield(1, 0);
        dungeon.addEntity(shield);

        Armour armour = new Armour(1, 0);
        dungeon.addEntity(armour);

        Sword sword = new Sword(1,0);
        dungeon.addEntity(sword);

        // Create Assassin at (10, 0)
        Assassin assassin = new Assassin(2, 0, dungeon);
        dungeon.addEntity(assassin);
        // battle assassin
        player.moveRight();
        player.battle(assassin);
        // player and assassin is alive
        assertTrue(dungeon.getEntityList().contains(player));
        assertTrue(dungeon.getEntityList().contains(assassin));

        for (int i = 0; i < 3; i++) {
            player.battle(assassin);
        }
        // Player is dead and assassin is alive after 4 ticks
        assertFalse(dungeon.getEntityList().contains(player));
        assertTrue(dungeon.getEntityList().contains(assassin));
    }

    // battle assassin with invincibility
    @Test
    public void testAssassinInvicible() {
        // DungeonManiaController controller = new DungeonManiaController();
        // controller.clearData();

        // controller.newGame("testBattleAssassin", "Standard");

        // controller.tick(null, Direction.RIGHT);

        // assertEquals(new EntityResponse("Mercenary0", "mercenary", new Position(4,0,3), true), controller.getInfo("Mercenary0"));
        // assertThrows(InvalidActionException.class, () -> controller.interact("Mercenary0"));
        // controller.tick(null, Direction.RIGHT);
        // controller.interact("Mercenary0");
    }


    // hydra spawns after 50 ticks in hard mode
    @Test
    public void testHydraSpawn() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("testHydraSpawn", "Hard");

        for (int i = 0; i < 50; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        // assert true, Hydra is in entity list
        //assertTrue();
    }

    // hydra increases health
    @Test
    public void testHydraBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.clearData();

        controller.newGame("testHydraSpawn", "Hard");

        for (int i = 0; i < 50; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        // assert true, Hydra is in entity list
        //assertTrue();
    }
    // hydra decreases health
    @Test
    public void testHydraIncrease() {
        // Dungeon dungeon = new Dungeon();

        // // Create player at (0, 0)
        // Player player = new Player(0, 0);
        // dungeon.addEntity(player);

        // Bow bow = new Bow(1, 0);
        // dungeon.addEntity(bow);

        // Shield shield = new Shield(1, 0);
        // dungeon.addEntity(shield);

        // Armour armour = new Armour(1, 0);
        // dungeon.addEntity(armour);

        // Sword sword = new Sword(1,0);
        // dungeon.addEntity(sword);

        // // Create Hydra at (10, 0)
        // Hydra hydra = new Hydra(1, 0, dungeon);
        // dungeon.addEntity(hydra);
        // // battle assassin
        // player.moveRight();
        // player.battle(hydra);
        // // player and assassin is alive
        // assertTrue(dungeon.getEntityList().contains(player));
        // assertTrue(dungeon.getEntityList().contains(hydra));
    }
    // anduril on hydra
    @Test
    public void testAndurilHydra(){
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        Bow bow = new Bow(1, 0);
        dungeon.addEntity(bow);

        Shield shield = new Shield(1, 0);
        dungeon.addEntity(shield);

        Armour armour = new Armour(1, 0);
        dungeon.addEntity(armour);

        Anduril sword = new Anduril(1, 0);
        dungeon.addEntity(sword);

        // Create Hydra at (10, 0)
        Hydra hydra = new Hydra(1, 0, dungeon);
        dungeon.addEntity(hydra);
        // battle assassin
        player.moveRight();
        player.battle(hydra);
        // player and assassin is alive
        assertTrue(dungeon.getEntityList().contains(player));
        assertTrue(dungeon.getEntityList().contains(hydra));
        // Hydra took damage
        assertTrue(hydra.getHealth() < 30);
    }
    // anduril on assassin
    @Test
    public void testAndurilAssassin() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        Bow bow = new Bow(1, 0);
        dungeon.addEntity(bow);

        Shield shield = new Shield(1, 0);
        dungeon.addEntity(shield);

        Armour armour = new Armour(1, 0);
        dungeon.addEntity(armour);

        Anduril sword = new Anduril(1, 0);
        dungeon.addEntity(sword);

        // Create Assassin at (1, 0)
        Assassin assassin = new Assassin(1, 0, dungeon);
        dungeon.addEntity(assassin);
        // battle assassin
        player.moveRight();
        player.battle(assassin);
        // player and assassin is alive
        assertTrue(dungeon.getEntityList().contains(player));
        assertTrue(dungeon.getEntityList().contains(assassin));
        // Hydra took damage
        assertTrue(assassin.getHealth() < 30);
    }
}
