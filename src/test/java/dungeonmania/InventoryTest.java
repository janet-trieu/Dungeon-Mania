package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

public class InventoryTest {
    /**
     * Unit testing for adding item to inventory
     */
    @Test
    public void testCollectItem(){
        // create inventory and treasure
        Inventory inventory = new Inventory();
        Treasure treasure0 = new Treasure(null, null);
        // Add treasure to inventory
        inventory.addItem(treasure0);
        // Treasure is no longer on dungeon
        assertEquals(new Position(null, null, null), treasure0.getPosition());
        // treasure is in inventory
        assertEquals(true, inventory.getItems().contains(treasure0));
    }

    /**
     * Unit testing for removing item to inventory
     */
    @Test
    public void testRemoveItem(){
        // create inventory and treasure
        Inventory inventory = new Inventory();
        Treasure treasure0 = new Treasure(null, null);
        // Add treasure to inventory
        inventory.addItem(treasure0);
        // Treasure is no longer on dungeon
        assertEquals(new Position(null, null, null), treasure0.getPosition());
        // treasure is in inventory
        assertEquals(true, inventory.getItems().contains(treasure0));

        // remove treasure from inventory
        inventory.removeItem(treasure0);
        // treasure is no longer in inventory
        assertEquals(false, inventory.getItems().contains(treasure0));
    }    

    /**
     * Unit testing for adding multiple items to inventory
     */
    @Test
    public void testCollectMultipleItem(){
        // create inventory and treasure
        Inventory inventory = new Inventory();
        Treasure treasure0 = new Treasure(null, null);
        Treasure treasure1 = new Treasure(null, null);
        // Add treasure0 and treasure1 to inventory
        inventory.addItem(treasure0);
        inventory.addItem(treasure1);
        // Treasure is no longer on dungeon
        assertEquals(new Position(null, null, null), treasure0.getPosition());
        assertEquals(new Position(null, null, null), treasure1.getPosition());
        // treasure is in inventory
        assertEquals(true, inventory.getItems().contains(treasure0));
        assertEquals(true, inventory.getItems().contains(treasure1));
    }

    /**
     * Unit Test add only 1 key
     */
    @Test
    public void testOnlyOneKeyItem(){
        // create inventory and treasure
        Inventory inventory = new Inventory();
        Key key0 = new Key(null, null);
        Key key1 = new Key(null, null);
        // Add key0 to inventory
        inventory.addItem(key0);
        
        // key is no longer on dungeon
        assertEquals(new Position(null, null, null), key0.getPosition());
        // key0 is in inventory
        assertEquals(true, inventory.getItems().contains(key0));

        // attempt to add key1 to inventory
        inventory.addItem(key1);
        // key2 is not in inventory
        assertEquals(false, inventory.getItems().contains(key1));
    }

    /**
     * System tests for adding item to inventory
     */
    @Test
    public void testSystemAddItem(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);   
        
        // create treasure at (1, 0)
        Treasure treasure0 = new Treasure(1, 0);
        dungeon.createEntity(treasure0);

        // player moves right
        player.moveRight();
        inventory.addItem(treasure0);

        // treasure0 is no longer on the dungeon
        assertEquals(new Position(null, null, null), treasure0.getPosition());
        // treasure0 is now in inventory
        assertEquals(true, inventory.getItems().contains(treasure0));
    }

    /**
     * system tests for using key at door
     */
    @Test
    public void testSystemAddItem(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.createEntity(player);

        // create key at (1, 0)
        Treasure key0 = new Key(1, 0);
        dungeon.createEntity(key0);
        key0.setId = 0;

        // create door at (2, 0)
        Door door0 = new Door(2, 0);
        dungeon.createEntity(door0);
        door.setId = 0;

        // player moves right and collects key
        player.moveRight();
        inventory.addItem(key0);
        assertEquals(new Position(null, null, null), key0.getPosition());
        assertEquals(true, inventory.getItems().contains(key0));

        // player moves right and opens door
        player.moveRight();
        player.interact(door0);

        // player is on same cell as door
        assertEquals(new Position(2, 0, 4), player.getPosition());
        // key is no longer in inventory
        assertEquals(false, inventory.getItems().contains(key0));
    }   
}
