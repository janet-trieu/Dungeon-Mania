package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.FloorSwitch;

public class SwitchGoal extends LeafGoal {

    /**
     * Constructor for SwitchGoal
     * @param dungeon
     */
    public SwitchGoal(Dungeon dungeon) {
        super(dungeon);
    }

    /**
     * toString method
     * return: 
     *  - ":switch" or
     *  - ""
     */
    @Override
    public String toString() {
        update();
        if (isComplete()) {
            return "";
        } else {
            return ":switch";
        }
    }

    /**
     * iterates through entityList in dungeon and check if all floor switches
     * are activated
     */
    @Override
    public void update() {
        List<FloorSwitch> switchList = switchList();
        setComplete(true);
        for (FloorSwitch floorSwitch : switchList) {
            if (!floorSwitch.getIsActive()) {
                setComplete(false);
            }
        }
    }

    /**
     * helper method to obtain list of all floor switches
     * @return
     */
    public List<FloorSwitch> switchList() {
        List<FloorSwitch> listOfFloorSwitch = new ArrayList<FloorSwitch>();
        List<Entity> entityList = getDungeon().getEntityList();
        for (Entity entity : entityList) {
            if (entity.getType().equals("switch")) {
                listOfFloorSwitch.add((FloorSwitch) entity);
            }
        }
        return listOfFloorSwitch;
    }
    
}
