package dungeonmania.entities.staticEntity;

public class FloorSwitch extends StaticEntity {
    private Boolean active;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public FloorSwitch(int x, int y) {
        super(x, y, "switch");
        setId("switch" + String.valueOf(counter));
        counter++;
    }

    public Boolean isActivated() {
        return active;
    }
}
