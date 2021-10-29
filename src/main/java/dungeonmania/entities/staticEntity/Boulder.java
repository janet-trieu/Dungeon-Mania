package dungeonmania.entities.staticEntity;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity {
    
    private int layer = 1;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Boulder(int x, int y) {
        super(x, y, "boulder");
        setId("boulder" + String.valueOf(counter));
        counter++;
        setLayer(layer);
    }

    public void push(Position currPos, Direction direction) {
        Position newPos = currPos.translateBy(direction);
        List<StaticEntity> existingStaticEntities = getStaticEntityList();
        for (StaticEntity entity : existingStaticEntities) {
            // if the new position of boulder is the same cell as floor switch
            // if (entity.getType().equals("floor_switch")) {
            if (entity.getType().equals("floor_switch")) {
                if (newPos.equals(entity.getPosition())) {
                
                }
            // if the new position of boulder is in the same cell as another static entity, remain in current position    
            } else {
                newPos = currPos;
            }
        }
    }

    /**
     * Method to return whether the switch is activated
     * @return
     */
    public Boolean isActivated() {
        
    }

}
