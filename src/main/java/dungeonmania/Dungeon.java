package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectableEntity.*;
import dungeonmania.entities.collectableEntity.buildableEntity.*;
import dungeonmania.entities.collectableEntity.potionEntity.*;
import dungeonmania.entities.collectableEntity.rareCollectableEntity.Anduril;
import dungeonmania.entities.collectableEntity.rareCollectableEntity.TheOneRing;
import dungeonmania.entities.movingEntity.*;
import dungeonmania.entities.staticEntity.*;
import dungeonmania.goals.Goal;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {

    // storing the required attributes
    private String dungeonName;
    private List<Entity> entityList;
    private List<CollectableEntity> spawnedCollectablesList;
    private List<String> buildableList;
    private Inventory inventory;
    private Goal goal;
    private static Dungeon thisDungeon = null;
    private String gameMode;
    private String dungeonId;

    /**
     * USED FOR TESTING
     */
    public Dungeon() {
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectablesList = new ArrayList<CollectableEntity>();
        this.buildableList = new ArrayList<String>();
        this.inventory = new Inventory();
        thisDungeon = this;
        resetCounters();
    }

    /**
     * Constructor for the dungeon
     * @param dungeonName
     * @param gameMode
     */
    public Dungeon(String dungeonName, String gameMode) {
        this.dungeonName = dungeonName;
        this.entityList = new ArrayList<Entity>();
        this.spawnedCollectablesList = new ArrayList<CollectableEntity>();
        this.buildableList = new ArrayList<String>();
        this.inventory = new Inventory();
        this.gameMode = gameMode;
        thisDungeon = this;
        resetCounters();
    }

    /**
     * Return the instance of the current dungeon
     * @return
     */
    public static Dungeon getDungeon() {
        return thisDungeon;
    }

    /**
     * Getter for the dungeon name
     * @return
     */
    public String getDungeonName() {
        return dungeonName;
    }

    /**
     * Getter for the list of entities that exists
     * @return
     */
    public List<Entity> getEntityList() {
        return entityList;
    }

    /**
     * Getter for the list of collectable entities that exists
     * @return
     */
    public List<CollectableEntity> getSpawnedCollectables() {
        return spawnedCollectablesList;
    }

    /**
     * Getter for the inventory
     * @return
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Getter for the goal/s
     * @return
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * Getter for the gamemode
     * @return
     */
    public String getGameMode() {
        return gameMode;
    }
    
    /**
     * Updates goal
     */
    public void updateGoal() {
        if (getGoal() != null) {
            goal.update();
        }
    }

    /**
     * USED FOR TESTING
     * Returns an entity's response info.
     * @param entityId
     * @return
     */
    public EntityResponse getInfo(String entityId) {
        EntityResponse response;

        for (int i = 0; i < getEntityList().size(); i++) {
            Entity entity = getEntityList().get(i);
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
                if (staticEntity.isPassable()) {

                } else if (staticEntity instanceof Door) {

                } else if (staticEntity instanceof Boulder) {

                } else {
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
        int sunStoneCounter = 0;
        for (CollectableEntity entity : inventory) {
            if (entity instanceof Wood) {
                woodCounter++;
            } else if (entity instanceof Treasure) {
                treasureCounter++;
            } else if (entity instanceof Key) {
                keyCounter++;
            } else if (entity instanceof SunStone) {
                sunStoneCounter++;
            }
        }

        if ((woodCounter >= 2 && treasureCounter >= 1) || (woodCounter >= 2 && keyCounter >= 1) 
            || (woodCounter >= 2 && sunStoneCounter >= 1)) {
                
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
    
    /**
     * Method to get a list of positions that are cardinally adjacent to the entity's position
     * P.S. distance = 2 cells
     * @param entity
     * @return
     */
    public List<Position> getCardinalAdjacent2Cells(Entity entity) {
        List<Position> positions = new ArrayList<Position>();
        int x = entity.getX();
        int y = entity.getY();
        positions.add(new Position(x, y - 2));
        positions.add(new Position(x + 2, y));
        positions.add(new Position(x, y + 2));
        positions.add(new Position(x - 2, y));

        return positions;
    }

    /**
     * Method to get a list of positions that are cardinally adjacent to the entity's position
     * P.S. distance = 1 cells
     * @param entity
     * @return
     */
    public List<Position> getCardinalAdjacentCell(Entity entity) {
        List<Position> positions = new ArrayList<Position>();
        int x = entity.getX();
        int y = entity.getY();
        positions.add(new Position(x, y - 1));
        positions.add(new Position(x + 1, y));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x - 1, y));

        return positions;
    }

    /**
     * Method to check whether the player and mercenary are within 2 cells of each other
     * @param otherEntity
     */
    public boolean checkBribeRange(Entity otherEntity) {
        Boolean twoCells = false;
        Boolean oneCell = false;
        Position otherPos = otherEntity.getPosition();
        List<Position> twoCellList = getCardinalAdjacent2Cells(getPlayer());
        List<Position> oneCellList = getCardinalAdjacentCell(getPlayer());

        // check if player and mercenary positions' are within 2 cardinally adjacent cells
        for (Position position : twoCellList) {
            if (position.equals(otherPos)) {
                twoCells = true;
                break;
            }
        }

        // check if player and mercanry positions' are cardinally adjacent
        for (Position position : oneCellList) {
            if (position.equals(otherPos)) {
                oneCell = true;
                break;
            }
        }

        // if distance between cells are greater than 2, return false
        if (oneCell == true || twoCells == true) {
            return true;
        // if distance between cells are within 2 cells, return true
        } else {
            return false;
        }
    }

    /**
     * Method to return a list of all current entity's ids
     * @return list of ids
     */
    public List<String> entityIdList() {
        List<String> entityIds = new ArrayList<String>();

        for (Entity entity : entityList) {
            entityIds.add(entity.getId());
        }

        return entityIds;
    }

    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<String> getBuildableList() {
        return buildableList;
    }

    public void setBuildableList(List<String> buildableList) {
        this.buildableList = buildableList;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }

    /**
     * Method to return a list of entities that are adjacent to a given entity
     * @param entity
     * @return
     */
    public List<Entity> adjacentEntityList(Entity entity) {
        List<Position> adjacentCells = entity.getPosition().getAdjacentPositions();
        List<Entity> existingEntities = getEntityList();
        List<Entity> adjacentEntities = new ArrayList<Entity>();

        for (Entity otherEntity : existingEntities) {
            if (adjacentCells.contains(otherEntity.getPosition())) {
                adjacentEntities.add(otherEntity);
            }
        }

        return adjacentEntities;
    }

    /**
     * Helper method to get a random position of 10 cells distance from player
     * @param entity
     * @return
     */
    public Position spawnDistance(Entity entity) {
        int x = entity.getPosition().getX();
        int y = entity.getPosition().getY();

        int lowX = x - 10;
        int highX = x + 10;
        int lowY = y - 10;
        int highY = y + 10;

        Random random = new Random();

        int distX = random.nextInt(highX - lowX) + lowX;
        int distY = random.nextInt(highY - lowY) + lowY;

        return new Position(distX, distY);
    }

    /**
     * Resets static counters when loading a game
     */
    public void resetCounters() {
        // Static IDs
        FloorSwitch.setCounter(0);
        Door.setCounter(0);
        Boulder.setCounter(0);
        Wall.setCounter(0);
        Exit.setCounter(0);
        Portal.setCounter(0);
        ZombieToastSpawner.setCounter(0);
        SwampTile.setCounter(0);

        // Moving IDs
        Spider.setCounter(0);
        ZombieToast.setCounter(0);
        Mercenary.setCounter(0);
        Assassin.setCounter(0);
        Hydra.setCounter(0);

        // Collectable IDs
        Treasure.setCounter(0);
        Key.setCounter(0);
        Arrow.setCounter(0);
        Wood.setCounter(0);
        Bomb.setCounter(0);
        InvincibilityPotion.setCounter(0);
        HealthPotion.setCounter(0);
        InvisibilityPotion.setCounter(0);
        TheOneRing.setCounter(0);
        Armour.setCounter(0);
        Sword.setCounter(0);
        Shield.setCounter(0);
        Bow.setCounter(0);
        SunStone.setCounter(0);
        Anduril.setCounter(0);
        Sceptre.setCounter(0);
        MidnightArmour.setCounter(0);

        // Tick counters
        Spider.setSpiderNum(0);
        Spider.setTickCounter(0);
        ZombieToastSpawner.setTickCounter(1);

        // TODO: ADD ANY OTHER MILESTONE 3 STATIC ID/TICK COUNTER RESETS!!!
    }
    
    /**
     * Checks if 'sceptre' is buildable.
     * If so and not already in 'buildableList', add to 'buildableList' and return true
     * @return boolean if sceptre is buildable
     */
    public Boolean updateBuildableListSceptre() {
        if (buildableList.contains("sceptre")) {
            return true;
        }
        Boolean bool = false;
        List<CollectableEntity> inventory = getInventory().getItems();
        int woodCounter = 0;
        int arrowCounter = 0;
        int treasureCounter = 0;
        int keyCounter = 0;
        int sunStoneCounter = 0;
        for (CollectableEntity entity : inventory) {
            if (entity instanceof Wood) {
                woodCounter++;
            } else if (entity instanceof Arrow) {
                arrowCounter++;
            } else if (entity instanceof Treasure) {
                treasureCounter++;
            } else if (entity instanceof Key) {
                keyCounter++;
            } else if (entity instanceof SunStone) {
                sunStoneCounter++;
            }
        }
        // Can be crafted with one wood or two arrows, one key or treasure, and one sun stone.
        if ((woodCounter >= 1 && keyCounter >= 1 && sunStoneCounter >= 1) ||        // 1 wood, 1 key, 1 sun stone
            (woodCounter >= 1 && treasureCounter >= 1 && sunStoneCounter >= 1) ||   // 1 wood, 1 treasure, 1 sun stone
            (woodCounter >= 1 && sunStoneCounter >= 2) ||                           // 1 wood, 2 sun stones
            (arrowCounter >= 2 && keyCounter >= 1 && sunStoneCounter >= 1) ||       // 2 arrows, 1 key, 1 sun stone
            (arrowCounter >= 2 && treasureCounter >= 1 && sunStoneCounter >= 1) ||  // 2 arrows, 1 treasure, 1 sun stone
            (arrowCounter >= 2 && sunStoneCounter >= 2)) {                          // 2 arrows, 2 sun stones
                
            buildableList.add("sceptre");
            bool = true;
        }
        return bool;
    }

    /**
     * Checks if 'midnight_armour' is buildable.
     * If so and not already in 'buildableList', add to 'buildableList' and return true
     * @return boolean if sceptre is buildable
     */
    public Boolean updateBuildableListMidnightArmour() {
        Boolean bool = false;
        List<MovingEntity> enemies = getMovingEntities();

        // check if there are any zombie toasts in dungeon
        for (MovingEntity enemy : enemies) {
            if (enemy instanceof ZombieToast) {
                return false;
            }
        }

        if (buildableList.contains("midnight_armour")) {
            return true;
        }

        List<CollectableEntity> inventory = getInventory().getItems();
        int armourCounter = 0;
        int sunStoneCounter = 0;
        for (CollectableEntity entity : inventory) {
            if (entity instanceof Armour) {
                armourCounter++;
            } else if (entity instanceof SunStone) {
                sunStoneCounter++;
            }
        }
        
        // Can be crafted with one armour and one sun stone
        if (armourCounter >= 1 && sunStoneCounter >= 1) {
            buildableList.add("midnight_armour");
            bool = true;
        }
                
        return bool;
    }

    public List<Bribeable> mindControlledEntities() {
        List<MovingEntity> movingEntities = getMovingEntities();
        List<Bribeable> mindControlledEntities = new ArrayList<Bribeable>();

        for (MovingEntity movingEntity : movingEntities) {
            if (movingEntity instanceof Bribeable) {
                Bribeable entity = (Bribeable)movingEntity;
                if (entity.isMindControlled()) {
                    mindControlledEntities.add(entity);
                }
            }
        }

        return mindControlledEntities;
    }

}
