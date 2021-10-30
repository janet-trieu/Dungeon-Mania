package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.FloorSwitch;

public class SwitchGoal extends LeafGoal {
    public SwitchGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String toString() {
        if (isComplete()) {
            return "";
        } else {
            return ":switch";
        }
    }

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
