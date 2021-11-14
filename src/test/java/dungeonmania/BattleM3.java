package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.buildableEntity.MidnightArmour;
import dungeonmania.entities.collectableEntity.buildableEntity.Shield;
import dungeonmania.entities.collectableEntity.rareCollectableEntity.Anduril;
import dungeonmania.entities.movingEntity.Assassin;
import dungeonmania.entities.movingEntity.Hydra;

public class BattleM3 {
    @Test
    public void testBattleHydra() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create Hydra at (1, 0)
        Hydra hydra = new Hydra(1, 0, dungeon);
        dungeon.addEntity(hydra);

        player.moveRight();
        player.battle(hydra);

        assertFalse(dungeon.getEntityList().contains(player));
    }

    @Test
    public void testBattleAssassin() {
        Dungeon dungeon = new Dungeon();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create Assassin at (1, 0)
        Assassin assassin = new Assassin(1, 0, dungeon);
        dungeon.addEntity(assassin);

        player.moveRight();
        player.battle(assassin);

        assertFalse(dungeon.getEntityList().contains(player));
    }

    @Test
    public void testBattleAndurilDies() {
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();
        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add anduril to inventory
        Anduril anduril = new Anduril(-1, -1);
        inventory.addItem(anduril);

        // Create Assassin at (1, 0)
        Assassin assassin = new Assassin(1, 0, dungeon);
        dungeon.addEntity(assassin);

        player.moveRight();
        player.battle(assassin);

        assertFalse(dungeon.getEntityList().contains(player));
    }

    @Test
    public void testBattleAndurilAndArmourAndShield() {
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();
        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // Create and add anduril to inventory
        Anduril anduril = new Anduril(-1, -1);
        inventory.addItem(anduril);

        // Create and add anduril to inventory
        Shield shield = new Shield(-1, -1);
        inventory.addItem(shield);

        // Create and add anduril to inventory
        MidnightArmour armour = new MidnightArmour(-1, -1);
        inventory.addItem(armour);

        // Create Assassin at (1, 0)
        Assassin assassin = new Assassin(1, 0, dungeon);
        dungeon.addEntity(assassin);

        player.moveRight();
        player.battle(assassin);

        assertTrue(dungeon.getEntityList().contains(player));
    }
}
