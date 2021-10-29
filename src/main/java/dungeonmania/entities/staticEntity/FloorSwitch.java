package dungeonmania.entities.staticEntity;

import java.util.ArrayList;
import java.util.List;

public class FloorSwitch extends StaticEntity {

    // storing the boolean of whether floor switch is activated by a boulder
    private Boolean active = false;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    // storing a list of static entities
    private List<FloorSwitch> floorSwitchList = new ArrayList<FloorSwitch>();

    public List<FloorSwitch> getFloorSwitchList() {
        return floorSwitchList;
    }

    public void setFloorSwitchList(List<FloorSwitch> floorSwitchList) {
        this.floorSwitchList = floorSwitchList;
    }

    /**
     * Floor switch constructor
     * @param x position
     * @param y position
     */
    public FloorSwitch(int x, int y) {
        super(x, y, "switch");
        setId("switch" + String.valueOf(counter));
        floorSwitchList.add(this);
        counter++;
    }

    /**
     * Method to return whether the switch is activated
     * @return
     */
    public Boolean isActivated() {
        
        return active;
    }
}
