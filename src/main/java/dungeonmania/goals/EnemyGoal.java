package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
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
        numberOfHostileMonster += getEntitiesOfType("zombieToast").size();
        numberOfHostileMonster += getEntitiesOfType("zombieToast").size();
        numberOfHostileMonster += getEntitiesOfType("zombieToastSpawner").size();
        numberOfHostileMonster += hostileMercenaryList().size();
        if (numberOfHostileMonster == 0) {
            setComplete(true);
        } else {
            setComplete(false);
        }
    }

    public List<Mercenary> mercenaryList() {
        List<Mercenary> listOfEntity = new ArrayList<Mercenary>();
        for (int i = 0; i < dungeon.getEntityList().size(); i++) {
            if (dungeon.getEntityList().get(i).getType().equals("mercenary")) {
                listOfEntity.add((Mercenary) dungeon.getEntityList().get(i));
            }
        }
        return listOfEntity;
    }

    public List<Mercenary> hostileMercenaryList() {
        List<Mercenary> mercenaryList = mercenaryList();
        List<Mercenary> listOfHostile = new ArrayList<Mercenary>();
        for (int i = 0; i < mercenaryList.size(); i++) {
            if (mercenaryList.get(i).getState().isHostile()) {
                listOfHostile.add(mercenaryList.get(i));
            }
        }
    }
}
