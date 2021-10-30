package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Mercenary;

public class EnemyGoal extends LeafGoal {
    public EnemyGoal(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public String toString() {
        if (isComplete()) {
            return "";
        } else {
            return ":enemy";
        }
    }

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

    public List<Mercenary> hostileMercenaryList() {
        List<Mercenary> mercenaryList = mercenaryList();
        List<Mercenary> listOfHostile = new ArrayList<Mercenary>();
        for (Mercenary mercenary : mercenaryList) {
            if (mercenary.isBribed()) {
                listOfHostile.add(mercenary);
            }
        }
    }
}
