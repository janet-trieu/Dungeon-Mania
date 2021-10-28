package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.staticEntity.FloorSwitch;

public class SwitchGoal extends LeafGoal {
    public SwitchGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String toString() {
        return ":switch";
    }

    @Override
    public void update() {
        List<FloorSwitch> switchList = switchList();
        setComplete(true);
        for (int i = 0; i < switchList.size(); i++) {
            if (!switchList.get(i).isActivated()) {
                setComplete(false);
            }
        }
    }

    public List<FloorSwitch> switchList() {
        List<FloorSwitch> listOfEntity = new ArrayList<FloorSwitch>();
        for (int i = 0; i < dungeon.getEntityList().size(); i++) {
            if (dungeon.getEntityList().get(i).getType().equals("switch")) {
                listOfEntity.add((FloorSwitch) dungeon.getEntityList().get(i));
            }
        }
        return listOfEntity;
    }
}
