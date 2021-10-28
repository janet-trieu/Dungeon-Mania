package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity.BuildableEntity;
import dungeonmania.goals.Goal;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Dungeon {
    private String dungeonId;
    private String dungeonName;
    private List<Entity> entityList;
    private List<CollectableEntity> spawnedCollectables;
    private Inventory inventory;
    private List<BuildableEntity> buildables;
    private Goal goal;

    public Dungeon() {
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectables = new ArrayList<CollectableEntity>();
        this.inventory = new Inventory();
        this.buildables = new ArrayList<BuildableEntity>();
    }

    public Dungeon(String dungeonId, String dungeonName) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectables = new ArrayList<CollectableEntity>();
        this.inventory = new Inventory();
        this.buildables = new ArrayList<BuildableEntity>();
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public List<CollectableEntity> getSpawnedCollectables() {
        return spawnedCollectables;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<BuildableEntity> getBuildables() {
        return buildables;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    /**
     * USED FOR TESTING
     * Returns an entity's response info.
     * @param entityId
     * @return
     */
    public EntityResponse getInfo(String entityId) {
        EntityResponse response;
        for (Entity entity : entityList) {
            if (entityId.equals(entity.getId())) {
                Position position = new Position(entity.getX(), entity.getY());
                response = new EntityResponse(entity.getId(), entity.getType(), position, entity.getIsInteractable());
                return response;
            }
        }
        return null;
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public void addGoal(Goal goal) {
    }

    /**
     * Returns a list of all Entity Responses in EntityList
     * @return
     */
    public List<EntityResponse> getEntityResponse() {
        List<EntityResponse> response = new ArrayList<EntityResponse>();
        for (Entity entity : entityList) {
            Position position = new Position(entity.getX(), entity.getY(), entity.getLayer());
            EntityResponse entityResponse = new EntityResponse(entity.getId(), entity.getType(), position, entity.getIsInteractable());
            response.add(entityResponse);
        }
        return response;
    }

    /**
     * Returns a list of all Item Responses in Inventory
     * @return
     */
    public List<ItemResponse> getItemResponse() {
        List<ItemResponse> response = new ArrayList<ItemResponse>();
        for (CollectableEntity entity : inventory.getItems()) {
            ItemResponse itemResponse = new ItemResponse(entity.getId(), entity.getType());
            response.add(itemResponse);
        }
        return response;
    }

    /**
     * Returns a list of all Buildable item types
     * @return
     */
    public List<String> getBuildableString() {
        List<String> response = new ArrayList<String>();
        for (BuildableEntity entity : buildables) {
            response.add(entity.getType());
        }
        return response;
    }
}
