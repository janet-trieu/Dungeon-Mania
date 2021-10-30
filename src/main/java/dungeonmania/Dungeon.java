package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.Arrow;
import dungeonmania.entities.collectableEntity.Bomb;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.Key;
import dungeonmania.entities.collectableEntity.Treasure;
import dungeonmania.entities.collectableEntity.Wood;
import dungeonmania.entities.movingEntity.MovingEntity;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.StaticEntity;
import dungeonmania.entities.staticEntity.ZombieToastSpawner;
import dungeonmania.goals.Goal;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private String dungeonName;
    private List<Entity> entityList;
    private List<CollectableEntity> spawnedCollectablesList;
    private List<String> buildableList;
    private Inventory inventory;
    private Goal goal;
    private static Dungeon thisDungeon = null;
    private String gameMode;

    public Dungeon() {
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectablesList = new ArrayList<CollectableEntity>();
        this.buildableList = new ArrayList<String>();
        this.inventory = new Inventory();
        thisDungeon = this;
    }

    public Dungeon(String dungeonName, String gameMode) {
        this.dungeonName = dungeonName;
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectablesList = new ArrayList<CollectableEntity>();
        this.buildableList = new ArrayList<String>();
        this.inventory = new Inventory();
        this.gameMode = gameMode;
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

    public Goal getGoal() {
        return goal;
    }

    public String getGameMode() {
        return gameMode;
    }

    /**
     * Add goal to goal
     * @param goal
     */
    public void addGoal(Goal goal) {
        this.goal = goal;
    }
    
    /**
     * Updates goal
     */
    public void updateGoal() {
        goal.update();
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

    /**
     * Adds entity to EntityList
     * @param entity
     */
    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    /**
     * Removes entity from EntityList
     * @param entity
     */
    public void removeEntity(Entity entity) {
        entityList.remove(entity);
    }

    /**
     * Add item to Inventory
     * @param item
     * @return
     */
    public Boolean addItem(CollectableEntity item) {
        return inventory.addItem(item);
    }

    /**
     * Remove item from Inventory
     * @param item
     */
    public void removeItem(CollectableEntity item) {
        inventory.removeItem(item);
    }

    /**
     * Get Key from Inventory
     * @return Key
     */
    public Key getKey() {
        return inventory.getKey();
    }

    /**
     * Get the Player
     * @return player
     */
    public Entity getPlayer() {
        for (Entity entity : entityList) {
            if (entity instanceof Player) {
                return entity;
            }
        }
        return null;
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
        return buildableList;
    }

    /**
     * Returns goal string
     * @return
     */
    public String getGoalString() {
        return goal.toString();
    }

    /**
     * Gets a list of entities on the same position as entity
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

    /**
     * Checks if player can go through a static entity in a specific position
     * @param position
     * @return boolean
     */
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

    /**
     * Gets a list of entities that is cardinally adjacent
     * @param position
     * @return lsit of entities cardinally adjacent
     */
    public List<Entity> getEntitiesCardinallyAdjacent(Position position) {
        List<Entity> listOfEntities = new ArrayList<Entity>();
        listOfEntities.addAll(getEntitiesOnSamePosition(position.translateBy(Direction.UP)));
        listOfEntities.addAll(getEntitiesOnSamePosition(position.translateBy(Direction.DOWN)));
        listOfEntities.addAll(getEntitiesOnSamePosition(position.translateBy(Direction.LEFT)));
        listOfEntities.addAll(getEntitiesOnSamePosition(position.translateBy(Direction.RIGHT)));
        return listOfEntities;
    }

    /**
     * Checks if 'bow' is buildable.
     * If so and not already in 'buildableList', add to 'buildableList' and return true
     * @return boolean if bow is buildable
     */
    public Boolean updateBuildableListBow() {
        if (buildableList.contains("bow")) {
            return true;
        }
        Boolean bool = false;
        List<CollectableEntity> inventory = Dungeon.getDungeon().getInventory().getItems();
        int arrowCounter = 0;
        int woodCounter = 0;
        for (CollectableEntity entity : inventory) {
            if (entity instanceof Arrow) {
                arrowCounter++;
            } else if (entity instanceof Wood) {
                woodCounter++;
            } 
        }

        if (arrowCounter >= 3 && woodCounter >= 1) {
            buildableList.add("bow");
            bool = true;
        }
        return bool;
    }

    /**
     * Checks if 'shield' is buildable.
     * If so and not already in 'buildableList', add to 'buildableList' and return true
     * @return boolean if shield is buildable
     */
    public Boolean updateBuildableListShield() {
        if (buildableList.contains("shield")) {
            return true;
        }
        Boolean bool = false;
        List<CollectableEntity> inventory = Dungeon.getDungeon().getInventory().getItems();
        int woodCounter = 0;
        int treasureCounter = 0;
        int keyCounter = 0;
        for (CollectableEntity entity : inventory) {
            if (entity instanceof Wood) {
                woodCounter++;
            } else if (entity instanceof Treasure) {
                treasureCounter++;
            } else if (entity instanceof Key) {
                keyCounter++;
            }
        }

        if ((woodCounter >= 2 && treasureCounter >= 1) || (woodCounter >= 2 && keyCounter >= 1)) {
            buildableList.add("shield");
            bool = true;
        }
        return bool;
    }

    /**
     * Gets all current MovingEntity in Dungeon
     * @return list of MovingEntity in Dungeon
     */
    public List<MovingEntity> getMovingEntities() {
        List<MovingEntity> movingList = new ArrayList<MovingEntity>();
        for (Entity entity : entityList) {
            if (entity instanceof MovingEntity) {
                movingList.add((MovingEntity)entity);
            }
        }
        return movingList;
    }

    /**
     * Gets all current ZombieToastSpawners in Dungeon
     * @return list of ZombieToastSpawnrs in Dungeon
     */
    public List<ZombieToastSpawner> getZombieToastSpawners() {
        List<ZombieToastSpawner> spawnerList = new ArrayList<ZombieToastSpawner>();
        for (Entity entity : entityList) {
            if (entity instanceof ZombieToastSpawner) {
                spawnerList.add((ZombieToastSpawner) entity);
            }
        }
        return spawnerList;
    }

    /**
     * Gets all current Bombs in Dungeon
     * @return list of Bombs in Dungeon
     */
    public List<Bomb> getBombs() {
        List<Bomb> bombList = new ArrayList<Bomb>();
        for (Entity entity : entityList) {
            if (entity instanceof Bomb) {
                bombList.add((Bomb) entity);
            }
        }
        return bombList;
    }
}
