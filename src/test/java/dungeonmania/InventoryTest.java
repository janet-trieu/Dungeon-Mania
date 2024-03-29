package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.collectableEntity.Treasure;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.util.Position;

public class InventoryTest {
    /**
     * Unit testing for adding item to inventory
     */
    @Test
    public void testCollectItem(){
        // create inventory and treasure
        Inventory inventory = new Inventory();
        Treasure treasure0 = new Treasure(1, 0);
        // Add treasure to inventory
        inventory.addItem(treasure0);
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
        Treasure treasure0 = new Treasure(1, 0);
        // Add treasure to inventory
        inventory.addItem(treasure0);
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
        Treasure treasure0 = new Treasure(1, 0);
        Treasure treasure1 = new Treasure(1, 0);
        // Add treasure0 and treasure1 to inventory
        inventory.addItem(treasure0);
        inventory.addItem(treasure1);
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
        Key key0 = new Key(1, 0, 0);
        Key key1 = new Key(1, 0, 1);
        // Add key0 to inventory
        inventory.addItem(key0);
        // key0 is in inventory
        assertEquals(true, inventory.getItems().contains(key0));

        // attempt to add key1 to inventory
        inventory.addItem(key1);
        // key2 is not in inventory
        assertEquals(false, inventory.getItems().contains(key1));
    }

    /**
     * Unit Test for adding item to inventory
     */
    @Test
    public void testAddItem() {
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);   
        
        // create treasure at (1, 0)
        Treasure treasure0 = new Treasure(1, 0);
        dungeon.addEntity(treasure0);

        // player moves right
        player.moveRight();
        inventory.addItem(treasure0);

        // treasure0 is no longer on the dungeon
        assertEquals(false, dungeon.getEntityList().contains(treasure0));
        // treasure0 is now in inventory
        assertEquals(true, inventory.getItems().contains(treasure0));
    }

    /**
     * Unit test for using key at door
     */
    @Test
    public void testKey(){
        Dungeon dungeon = new Dungeon();
        Inventory inventory = dungeon.getInventory();

        // Create player at (0, 0)
        Player player = new Player(0, 0);
        dungeon.addEntity(player);

        // create key at (1, 0)
        Key key0 = new Key(1, 0, 0);
        dungeon.addEntity(key0);

        // create door at (2, 0)
        Door door0 = new Door(2, 0, 0);
        dungeon.addEntity(door0);

        // player moves right and collects key
        player.moveRight();
        inventory.addItem(key0);
        assertEquals(false, dungeon.getEntityList().contains(key0));
        assertEquals(true, inventory.getItems().contains(key0));

        // player moves right and opens door
        player.moveRight();
        door0.insertKey(key0);

        // player is on same cell as door
        assertEquals(new Position(2, 0, 4), player.getPosition());
        // key is no longer in inventory
        assertEquals(false, dungeon.getEntityList().contains(key0));
    }   

}
