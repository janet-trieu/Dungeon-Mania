package dungeonmania.entities.staticEntity;

import dungeonmania.entities.collectableEntity.Key;

public class Door extends StaticEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // storing the corresponding key 
    private int key;

    /**
     * Constructor for a door that is locked
     * @param x
     * @param y
     * @param key
     */
    public Door(int x, int y, int key) {
        super(x, y, "door");
        setId("Door" + String.valueOf(counter));
        counter++;
        setKey(key);
    }

    /**
     * Constructor for a door that is unlocked
     * @param x
     * @param y
     * @param isPassable
     */
    public Door(int x, int y, Boolean isPassable) {
        super(x, y, "door");
        setId("Door" + String.valueOf(counter));
        counter++;
        setPassable(isPassable);
    }

    /**
     * Method that checks whether the key in player's inventory is the corresponding key to the door
     * @param key
     * @return
     */
    public boolean insertKey(Key key) {
        boolean keyWorked = false;
        if (key.getKeyId() == getKey()) {
            setPassable(true);
            keyWorked = true;
        }
        return keyWorked;
    }

    /**
     * Getter for corresponding key 
     */
    public int getKey() {
        return key;
    }

    /**
     * Setter for corresponding key
     */
    public void setKey(int key) {
        this.key = key;
    }
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Door.counter = counter;
    }
}
