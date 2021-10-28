package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.goals.Goal;
import dungeonmania.response.models.EntityResponse;

public class Dungeon {
    private Inventory inventory;
    private List<Entity> entityList = new ArrayList<Entity>();
    private List<CollectableEntity> spawnedCollectables = new ArrayList<CollectableEntity>();
    private Goal goal;

    public Dungeon() {
        // TODO
    }

    public EntityResponse getInfo(String entityId) {
        // TODO
        return null;
    }

    public void createEntity(Entity entity) {
        // TODO
    }

    public void addGoal(Goal goal) {
        // TODO
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<CollectableEntity> getSpawnedCollectables() {
        return spawnedCollectables;
    }

    public void setSpawnedCollectables(List<CollectableEntity> spawnedCollectables) {
        this.spawnedCollectables = spawnedCollectables;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    
}
