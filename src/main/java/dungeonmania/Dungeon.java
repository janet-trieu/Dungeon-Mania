package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity.BuildableEntity;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.StaticEntity;
import dungeonmania.goals.Goal;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private String dungeonName;
    private List<Entity> entityList;
    private List<CollectableEntity> spawnedCollectablesList;
    private List<BuildableEntity> buildableList;
    private Inventory inventory;
    private Goal goal;
    private static Dungeon thisDungeon = null;
    private String gamemode;

    public Dungeon() {
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectablesList = new ArrayList<CollectableEntity>();
        this.buildableList = new ArrayList<BuildableEntity>();
        this.inventory = new Inventory();
        this.gamemode = gamemode;
        thisDungeon = this;
    }

    public Dungeon(String dungeonName) {
        this.dungeonName = dungeonName;
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectablesList = new ArrayList<CollectableEntity>();
        this.buildableList = new ArrayList<BuildableEntity>();
        this.inventory = new Inventory();
        this.gamemode = gamemode;
        thisDungeon = this;
    }

    /**
     * Return the instance of the current dungeon
     * @return
     */
    public static Dungeon getDungeon() {
        return thisDungeon;
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

    public void updateGoal() {
        goal.update();
    }
    public String getGamemode() {
        return gamemode;
    }

    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
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
                Position position = new Position(entity.getX(), entity.getY(), entity.getLayer());
                response = new EntityResponse(entity.getId(), entity.getType(), position, entity.getIsInteractable());
                return response;
            }
        }
        return null;
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public void removeEntity(Entity entity) {
        entityList.remove(entity);
    }

    public Boolean addItem(CollectableEntity item) {
        return inventory.addItem(item);
    }

    public void removeItem(CollectableEntity item) {
        inventory.removeItem(item);
    }

    public Key getKey() {
        return inventory.getKey();
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
    public List<Entity> getEntitiesOnSamePosition(Position position) {
        List<Entity> listOfEntities = new ArrayList<Entity>();
        for (Entity otherEntity : entityList) {
            // if entity is on the same cell as position, add to list
            if (otherEntity.getPosition().equals(position)) {
                listOfEntities.add(otherEntity);
            }
        }
        return listOfEntities;
    }

    public Boolean canPlayerGoThrough(Position position) {
        Boolean canGoThrough = true;
        for (Entity entity : getEntitiesOnSamePosition(position)) {
            if (entity instanceof StaticEntity) {
                StaticEntity staticEntity = (StaticEntity) entity;
                if (!(staticEntity instanceof Door) && !(staticEntity instanceof Boulder) 
                    && !staticEntity.isPassable()) {
                    canGoThrough = false;
                }
            }
        }
        return canGoThrough;
    }

    public List<Entity> getEntitiesCardinallyAdjacent(Position position) {
        List<Entity> listOfEntities = new ArrayList<Entity>();
        listOfEntities.addAll(getEntitiesOnSamePosition(position.translateBy(Direction.UP)));
        listOfEntities.addAll(getEntitiesOnSamePosition(position.translateBy(Direction.DOWN)));
        listOfEntities.addAll(getEntitiesOnSamePosition(position.translateBy(Direction.LEFT)));
        listOfEntities.addAll(getEntitiesOnSamePosition(position.translateBy(Direction.RIGHT)));
        return listOfEntities;
    }
}
