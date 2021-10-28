package dungeonmania.entities.staticEntity;

public class FloorSwitch extends StaticEntity {
    private Boolean active;

    public FloorSwitch(int x, int y) {
        super(x, y, "switch");
    }

    public Boolean isActivated() {
        return active;
    }
}
