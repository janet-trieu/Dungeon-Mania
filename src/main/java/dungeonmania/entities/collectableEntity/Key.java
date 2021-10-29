package dungeonmania.entities.collectableEntity;

public class Key extends CollectableEntity {
    private int keyId;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Key(int x, int y, int keyId) {
        super(x, y, "key");
        setId("Key" + String.valueOf(counter));
        counter++;
        setKeyId(keyId);
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }
}
