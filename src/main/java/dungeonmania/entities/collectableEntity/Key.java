package dungeonmania.entities.collectableEntity;

public class Key extends CollectableEntity {
    private int keyId;

    public Key(int x, int y) {
        super(x, y, "key");
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }
}
