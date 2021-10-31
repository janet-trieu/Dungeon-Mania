package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Mercenary;

public class EnemyGoal extends LeafGoal {

    /**
     * Constructor for EnemyGoal
     * @param dungeon
     */
    public EnemyGoal(Dungeon dungeon) {
        super(dungeon);
    }

    /**
     * toString method
     * return: 
     *  - ":enemy" or
     *  - ""
     */
    @Override
    public String toString() {
        if (isComplete()) {
            return "";
        } else {
            return ":enemy";
        }
    }

    /**
     * iterates through entityList and see if there are any remaining enemies
     * if there are no enemies, goal is complete
     */
    @Override
    public void update() {
        int numberOfHostileMonster = getEntitiesOfType("spider").size();
        numberOfHostileMonster += getEntitiesOfType("zombie_toast").size();
        numberOfHostileMonster += getEntitiesOfType("zombie_toast_spawner").size();
        numberOfHostileMonster += hostileMercenaryList().size();
        if (numberOfHostileMonster == 0) {
            setComplete(true);
        } else {
            setComplete(false);
        }
    }

    /**
     * Helper method to obtain list of mercenaries
     * @return
     */
    public List<Mercenary> mercenaryList() {
        List<Mercenary> listOfEntity = new ArrayList<Mercenary>();
        List<Entity> entityList = getDungeon().getEntityList();
        for (Entity entity : entityList) {
            if (entity.getType().equals("mercenary")) {
                listOfEntity.add((Mercenary) entity);
            }
        }
        return listOfEntity;
    }

    /**
     * Helper method to obtain list of hostile mercenaries (not bribed)
     * @return
     */
    public List<Mercenary> hostileMercenaryList() {
        List<Mercenary> mercenaryList = mercenaryList();
        List<Mercenary> listOfHostile = new ArrayList<Mercenary>();
        for (Mercenary mercenary : mercenaryList) {
            if (mercenary.IsBribed()) {
                listOfHostile.add(mercenary);
            }
        }
        return listOfHostile;
    }
    
}
