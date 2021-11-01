package dungeonmania.entities.collectableEntity;

public class Key extends CollectableEntity {

    // storing the key id to find the key's corresponding door
    private int keyId;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a key
     * @param x position
     * @param y position
     * @param keyId of key
     */
    public Key(int x, int y, int keyId) {
        super(x, y, "key");
        setId("Key" + String.valueOf(counter));
        counter++;
        setKeyId(keyId);
    }

    /**
     * Getter for key's id
     * @return
     */
    public int getKeyId() {
        return keyId;
    }

    /**
     * Setter for key's id
     * @param keyId
     */
    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }
    
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Key.counter = counter;
    }
    
}
