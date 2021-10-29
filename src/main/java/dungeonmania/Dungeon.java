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
    private String dungeonName;
    private List<Entity> entityList;
    private List<CollectableEntity> spawnedCollectablesList;
    private List<BuildableEntity> buildableList;
    private Inventory inventory;
    private Goal goal;

    public Dungeon() {
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectablesList = new ArrayList<CollectableEntity>();
        this.buildableList = new ArrayList<BuildableEntity>();
        this.inventory = new Inventory();
    }

    public Dungeon(String dungeonName) {
        this.dungeonName = dungeonName;
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectablesList = new ArrayList<CollectableEntity>();
        this.buildableList = new ArrayList<BuildableEntity>();
        this.inventory = new Inventory();
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public List<CollectableEntity> getSpawnedCollectables() {
        return spawnedCollectablesList;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<BuildableEntity> getBuildableList() {
        return buildableList;
    }

    public Goal getGoal() {
        return goal;
    }

    public void addGoal(Goal goal) {
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
        for (BuildableEntity entity : buildableList) {
            response.add(entity.getType());
        }
        return response;
    }

    /**
     * Returns goal string
     * @return
     */
    public String getGoalString() {
        return goal.toString();
    }

    /**
     * 
     * @return list of entities on same position as entity
     */
    public List<Entity> getEntitiesOnSamePosition(Entity entity) {
        List<Entity> listOfEntities = new ArrayList<Entity>();
        for (Entity otherEntity : entityList) {
            // if other entity is not entity and have the same position, add to 
            if (!otherEntity.equals(entity) && otherEntity.getPosition().equals(entity.getPosition()) {
                listOfEntities.add(otherEntity);
            }
        }
        return listOfEntities;
    }
}
