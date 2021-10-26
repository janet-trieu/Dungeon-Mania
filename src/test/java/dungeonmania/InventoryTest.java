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
        Treasure treasure0 = new Treasure();
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
        Treasure treasure0 = new Treasure();
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
        Treasure treasure0 = new Treasure();
        Treasure treasure1 = new Treasure();
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
        Key key0 = new Key();
        Key key1 = new Key();
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
     * 
     */
}
