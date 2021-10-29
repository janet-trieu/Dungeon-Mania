package dungeonmania.entities.staticEntity;

import dungeonmania.entities.collectableEntity.Key;

public class Door extends StaticEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    //
    private int key;

    public Door(int x, int y, int key) {
        super(x, y, "door");
        setId("Door" + String.valueOf(counter));
        counter++;
        setKey(key);
    }

    public Door(int x, int y, Boolean isPassable) {
        super(x, y, "door");
        setId("Door" + String.valueOf(counter));
        counter++;
        setPassable(isPassable);
    }

    public boolean insertKey(Key key) {
        boolean keyWorked = false;
        if (key.getKeyId() == getKey()) {
            setPassable(true);
            keyWorked = true;
        }
        return keyWorked;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
