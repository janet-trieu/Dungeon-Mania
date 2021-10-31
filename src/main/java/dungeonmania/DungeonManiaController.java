package dungeonmania;

import dungeonmania.goals.*;
import dungeonmania.entities.*;
import dungeonmania.entities.staticEntity.*;
import dungeonmania.entities.movingEntity.*;
import dungeonmania.entities.collectableEntity.*;
import dungeonmania.entities.collectableEntity.rareCollectableEntity.*;
import dungeonmania.entities.collectableEntity.potionEntity.*;
import dungeonmania.entities.collectableEntity.breakableEntity.*;
import dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.sound.sampled.Port;

import org.json.JSONArray;
import org.json.JSONObject;

public class DungeonManiaController {
    private Dungeon dungeon;
    private Path savesPath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/saves");

    public DungeonManiaController() {
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    public List<String> getUsableItems() {
        return Arrays.asList("bomb", "health_potion", "invincibility_potion", "invisibility_potion");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Creates a new game
     * @param dungeonName
     * @param gameMode
     * @return dungeonName's DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        List<String> maps = null;
        try {
            maps = FileLoader.listFileNamesInResourceDirectory("dungeons");
        } catch (IOException e) {
            System.err.println("Invalid directory: " + e.getMessage());
        }

        try {
            // EXCEPTION CHECKS //
            // If dungeonName does not exist
            if (!maps.contains(dungeonName)) {
                throw new IllegalArgumentException("Invalid dungeonName");
            }
            // If gameMode is invalid
            if (!getGameModes().contains(gameMode)) {
                throw new IllegalArgumentException("Invalid gameMode");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        // If '...src/main/resources/saves' doesn't exist, create it (automatically creates in bin file as well)
        File savesDirectory = new File(savesPath.toString());
        if (!savesDirectory.exists()) {
            try {
                Files.createDirectories(savesPath);
            }
            catch (IOException e) {
                System.err.println("Path error in saveGame: " + e.getMessage());
            }
        }


        // Generate dungeonId
        String dungeonId = dungeonName + Instant.now().getEpochSecond();

        // Initialise dungeon
        dungeon = new Dungeon(dungeonName, gameMode);

        // Load the map
        String map = "";
        try {
            map = FileLoader.loadResourceFile("dungeons/" + dungeonName +".json");
        } catch (IOException e) {
            System.err.println("Invalid file: " + e.getMessage());
        }

        JSONObject mapObj = new JSONObject(map);

        // Add "entities"
        JSONArray mapEntities = mapObj.getJSONArray("entities");
        addEntitiesDungeon(mapEntities);
        
        // Add "goals"
        if (mapObj.has("goal-condition")) {
            JSONObject goalObj = mapObj.getJSONObject("goal-condition");
            Goal goal = createGoal(goalObj, dungeon);
            dungeon.addGoal(goal);
            return new DungeonResponse(dungeonId, dungeonName, dungeon.getEntityResponse(), dungeon.getItemResponse(), dungeon.getBuildableString(), dungeon.getGoalString());
        }

        Goal goal = new ExitGoal(dungeon);
        dungeon.addGoal(goal);

        // For maps that do not have goals
        return new DungeonResponse(dungeonId, dungeonName, dungeon.getEntityResponse(), dungeon.getItemResponse(), dungeon.getBuildableString(), dungeon.getGoalString());
    }

    /**
     * Calls create entity to create class and if needs more attributes, add. Then add to dungeon
     * @param mapEntities
     */
    private void addEntitiesDungeon(JSONArray mapEntities) {
        for (int i = 0; i < mapEntities.length(); i++) {
            JSONObject obj = mapEntities.getJSONObject(i);
            Entity entity = createEntity(obj);
            if (entity instanceof Player) {
                if (obj.has("health")) {
                    Player player = (Player) entity;
                    player.setHealth(obj.getInt("health"));
                    int invinDur = obj.getInt("invincibilityDuration");
                    int invisDur = obj.getInt("invisibilityDuration");
                    int bowDur = obj.getInt("bowDurability");
                    int armourDur = obj.getInt("armourDurability");
                    int swordDur = obj.getInt("swordDurability");
                    int shieldDur = obj.getInt("shieldDurability");
                    player.setPlayerStates(invinDur, invisDur, bowDur, armourDur, swordDur, shieldDur);
                }
            } else if (entity instanceof ZombieToast) {
                if (obj.has("hasArmour")) {
                    ZombieToast zombieToast = (ZombieToast) entity;
                    zombieToast.setHasArmour(obj.getBoolean("hasArmour"));
                }
            } else if (entity instanceof Mercenary) {
                if (obj.has("hasArmour")) {
                    Mercenary mercenary = (Mercenary) entity;
                    mercenary.setHasArmour(obj.getBoolean("hasArmour"));
                    mercenary.setIsBribed(obj.getBoolean("isBribed"));
                }
            } else if (entity instanceof ZombieToastSpawner) {
                if (obj.has("tickCounter")) {
                    ZombieToastSpawner.setTickCounter(obj.getInt("tickCounter"));
                }
            }
            dungeon.addEntity(entity);
        }
    }

    /**
     * Loop through JSON "entities" and adds each data to the Dungeon class
     * @param mapObj
     */
    private Entity createEntity(JSONObject obj) {
        int x = -1;
        int y = -1;
        int keyId = -1;
        String colour = "";
        if (obj.has("x")) {
            x = obj.getInt("x");
            y = obj.getInt("y");
        }
        String type = obj.getString("type");
        
        if (obj.has("key")) {
            keyId = obj.getInt("key");
        }
        if (obj.has("colour")) {
            colour = obj.getString("colour");
        }

        switch (type) {
            case "armour":
                Armour armour = new Armour(x, y);
                return armour;
            case "arrow":
                Arrow arrow = new Arrow(x, y);
                return arrow;
            case "bomb":
                Bomb bomb = new Bomb(x, y);
                return bomb;
            case "boulder":
                Boulder boulder = new Boulder(x, y);
                return boulder;
            case "bow":
                Bow bow = new Bow(x, y);
                return bow;
            case "door":
                Door doorClosed = new Door(x, y, keyId);
                return doorClosed;
            case "exit":
                Exit exit = new Exit(x, y);
                return exit;
            case "health_potion":
                HealthPotion healthPotion = new HealthPotion(x, y);
                return healthPotion;
            case "invincibility_potion":
                InvincibilityPotion invincibilityPotion = new InvincibilityPotion(x, y);
                return invincibilityPotion;
            case "invisibility_potion":
                InvisibilityPotion invisibilityPotion = new InvisibilityPotion(x, y);
                return invisibilityPotion;
            case "key":
                Key key = new Key(x, y, keyId);
                return key;
            case "mercenary":
                Mercenary mercenary = new Mercenary(x, y, dungeon);
                return mercenary;
            case "player":
                Player player = new Player(x, y);
                return player;
            case "portal":
                Portal portal = new Portal(x, y, colour);
                return portal;
            case "shield":
                Shield shield = new Shield(x, y);
                return shield;
            case "spider":
                Spider spider = new Spider(x, y, dungeon);
                return spider;
            case "switch":
                FloorSwitch floorSwitch = new FloorSwitch(x, y);
                return floorSwitch;
            case "sword":
                Sword sword = new Sword(x, y);
                return sword;
            case "the_one_ring":
                TheOneRing oneRing = new TheOneRing(x, y);
                return oneRing;
            case "treasure":
                Treasure treasure = new Treasure(x, y);
                return treasure;
            case "wall":
                Wall wall = new Wall(x, y);
                return wall;
            case "wood":
                Wood wood = new Wood(x, y);
                return wood;
            case "zombie_toast":
                ZombieToast zombieToast = new ZombieToast(x, y, dungeon);
                return zombieToast;
            case "zombie_toast_spawner":
                ZombieToastSpawner zombieToastSpawner = new ZombieToastSpawner(x, y);
                return zombieToastSpawner;
            default:
                break;
        }
        return null;
    }
    
    /**
     * Loop through JSON "goal-condition" and "subgoals" (if exists) and adds data to the Dungeon class
     * @param mapObj     
     * @param dungeon
     * @return Goal
     */
    private Goal createGoal(JSONObject goalObj, Dungeon dungeon) {

        String goal = goalObj.getString("goal");

        if (goal.equals("AND") || goal.equals("OR")) {
            CompositeGoal compositeGoal;
            if (goal.equals("AND")) {
                compositeGoal = new AndGoal(dungeon);
            } else {
                compositeGoal = new OrGoal(dungeon);
            }
            JSONArray subgoals = goalObj.getJSONArray("subgoals");
            for (int i = 0; i < subgoals.length(); i++) {
                Goal leafGoal = createGoal(subgoals.getJSONObject(i), dungeon);
                compositeGoal.addSubGoal(leafGoal);
            }
            return compositeGoal;
        }

        switch (goal) {
            case "exit":
                return new ExitGoal(dungeon);
            case "enemies":
                return new EnemyGoal(dungeon);
            case "boulders":
                return new SwitchGoal(dungeon);
            case "treasure":
                return new TreasureGoal(dungeon);
            default:
                return null;
        }
    }

    /**
     * Saves game
     * @param name
     * @return dungeonResponse
     */
    public DungeonResponse saveGame(String name) {
        Dungeon currDungeon = Dungeon.getDungeon();

        String dungeonId = currDungeon.getDungeonName() + Instant.now().getEpochSecond();

        DungeonResponse response = new DungeonResponse(dungeonId, currDungeon.getDungeonName(), currDungeon.getEntityResponse(),
                                                    currDungeon.getItemResponse(), currDungeon.getBuildableString(), currDungeon.getGoalString());

        // Create .json file named 'name + .json'
        String filePath = savesPath.toString() + "\\" + name + ".json";
        try {
            File save = new File(filePath);
            save.createNewFile();
        } catch (IOException e) {
            System.err.println("File creation error in saveGame: " + e.getMessage());
        }

        // Save general game info
        JSONObject saveObj = new JSONObject();
        saveObj.put("filename", name);
        saveObj.put("gameMode", currDungeon.getGameMode());
        saveObj.put("dungeonId", dungeonId);
        saveObj.put("dungeonName", currDungeon.getDungeonName());
        saveObj.put("goals", currDungeon.getGoalString());
        
        // Save all entities in entityList and update ID counter
        JSONArray entityArray = new JSONArray();
        for (Entity entity : currDungeon.getEntityList()) {
            JSONObject entityInfo = new JSONObject();
            entityInfo.put("id", entity.getId());
            entityInfo.put("type", entity.getType());
            entityInfo.put("x", entity.getX());
            entityInfo.put("y", entity.getY());

            if (entity instanceof Player) {
                Player player = (Player) entity;
                entityInfo.put("health", player.getHealth());
                entityInfo.put("invincibilityDuration", player.getInvincibilityDuration());
                entityInfo.put("invisibilityDuration", player.getInvisibilityDuration());
                entityInfo.put("bowDurability", player.getBowDurability());
                entityInfo.put("armourDurability", player.getArmourDurability());
                entityInfo.put("swordDurability", player.getSwordDurability());
                entityInfo.put("shieldDurability", player.getShieldDurability());
            } else if (entity instanceof ZombieToast) {
                ZombieToast zombieToast = (ZombieToast) entity;
                entityInfo.put("hasArmour", zombieToast.getHasArmour());
            } else if (entity instanceof Mercenary) {
                Mercenary mercenary = (Mercenary) entity;
                entityInfo.put("hasArmour", mercenary.getHasArmour());
                entityInfo.put("isBribed", mercenary.IsBribed());
            } else if (entity instanceof Portal) {
                Portal portal = (Portal) entity;
                entityInfo.put("colour", portal.getColour());
            } else if (entity instanceof Door) {
                Door door = (Door) entity;
                entityInfo.put("key", door.getKey());
            } else if (entity instanceof Key) {
                Key key = (Key) entity;
                entityInfo.put("key", key.getKeyId());
            } else if (entity instanceof ZombieToastSpawner) {
                entityInfo.put("tickCounter", ZombieToastSpawner.getTickCounter());
            }
            entityArray.put(entityInfo);
        }
        saveObj.put("entities", entityArray);

        // Save all items in inventory
        JSONArray inventoryArray = new JSONArray();
        Inventory inventory = currDungeon.getInventory();
        for (CollectableEntity item : inventory.getItems()) {
            JSONObject itemInfo = new JSONObject();
            itemInfo.put("id", item.getId());
            itemInfo.put("type", item.getType());
            inventoryArray.put(itemInfo);
        }
        saveObj.put("inventory", inventoryArray);

        // Save all current buildable items
        JSONArray buildableArray = new JSONArray();
        for (String item : currDungeon.getBuildableString()) {
            buildableArray.put(item);
        }
        saveObj.put("buildables", buildableArray);       

        // TODO: Save all animations for animated entities - Should be a JSONArray: animations: []

        // Insert object into file
         try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(saveObj.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println("Data creation error in saveGame: " + e.getMessage());
        }

        return response;
    }

    /**
     * Loads game
     * @param name
     * @return DungeonResponse of selected dungeon
     * @throws IllegalArgumentException
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        dungeon = new Dungeon();
        File gameFile = new File(savesPath + "\\" + name + ".json");

        // EXCEPTION CHECKING
        // If filename does not exist/valid
        if (!gameFile.exists()) {
            throw new IllegalArgumentException("Name is not a valid game name");
        }

        // Read gameMode and dungeonResponse data
        String data = "";
        try {
            data = FileLoader.loadResourceFile("saves/" + name + ".json");
        } catch (IOException e) {
            System.err.println("File load error: " + e.getMessage());
        }

        // Set general dungeon info
        JSONObject dataObj = new JSONObject(data);
        String dungeonName = dataObj.getString("dungeonName");
        String gameMode = dataObj.getString("gameMode");
        String dungeonId = dataObj.getString("dungeonId");
        dungeon.setDungeonName(dungeonName);
        dungeon.setGameMode(gameMode);
        dungeon.setDungeonId(dungeonId);
        
        // Add entities and set necessary attributes, placing in Dungeon
        JSONArray mapEntities = dataObj.getJSONArray("entities");
        addEntitiesDungeon(mapEntities);
        
        // Get inventory (id, type), set necessary attributes
        Inventory inventory = new Inventory();
        JSONArray inventoryJSON = dataObj.getJSONArray("inventory");
        for (int i = 0; i < inventoryJSON.length(); i++) {
            JSONObject itemObj = inventoryJSON.getJSONObject(i);
            CollectableEntity item = (CollectableEntity) createEntity(itemObj);
            inventory.addItem(item);
        }
        dungeon.setInventory(inventory);
        
        // Set buildables
        List<String> buildable = new ArrayList<String>();
        JSONArray buildableJSONArray = dataObj.getJSONArray("buildables");
        for (int i = 0; i < buildableJSONArray.length(); i++) {
            String buildableType = (String) buildableJSONArray.get(i);
            buildable.add(buildableType);
        }
        dungeon.setBuildableList(buildable);

        // Get goal
        Goal goal = createGoalFromString(dataObj.getString("goals"), dungeon);
        
        dungeon.setGoal(goal);

        // Create new dungeonResponse from dungeon class to ensure its correct and return
        DungeonResponse response = new DungeonResponse(dungeonId, dungeon.getDungeonName(), dungeon.getEntityResponse(),
                                                    dungeon.getItemResponse(), dungeon.getBuildableString(), dungeon.getGoalString());
        // TODO: Animation loading

        return response;
    }

    /**
     * Makes a goal from a string
     * Unsure if this works for composite goals
     * @param goal
     * @param dungeon
     * @return
     */
    private Goal createGoalFromString(String goal, Dungeon dungeon) {
        if (goal.contains("(")) {
            CompositeGoal compositeGoal;
            if (goal.equals("AND")) {
                compositeGoal = new AndGoal(dungeon);
            } else {
                compositeGoal = new OrGoal(dungeon);
            }
            //TOKENISE
            String[] split = goal.split(" ");
            for (int i = 0; i < split.length; i++) {
                Goal leafGoal = createGoalFromString(split[i], dungeon);
                compositeGoal.addSubGoal(leafGoal);
            }
            return compositeGoal;
        }

        if (goal.contains("exit")) {
            return new ExitGoal(dungeon);
        } else if (goal.contains("enemies")) {
            return new EnemyGoal(dungeon);
        } else if (goal.contains("boulders")) {
            return new SwitchGoal(dungeon);
        } else if (goal.contains("treasure")) {
            return new TreasureGoal(dungeon);
        } else {
            return null;
        }
    }

    /**
     * Returns a list containing all the saved games that are currently stored
     * @return list of saved games
     */
    public List<String> allGames() {
        List<String> maps = null;
        try {
            maps = FileLoader.listFileNamesInResourceDirectory("/saves");
        } catch (IOException e) {
            System.err.println("Save directory does not exist: " + e.getMessage());
        }
        return maps;
    }

    /**
     * Tick game state
     * @param itemUsed
     * @param movementDirection
     * @return new dungeon state
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        Dungeon currDungeon = Dungeon.getDungeon();
        Inventory currInventory = currDungeon.getInventory();
        Player player = (Player) currDungeon.getPlayer();
        List<MovingEntity> enemyList = currDungeon.getMovingEntities();
        List<ZombieToastSpawner> spawnerList = currDungeon.getZombieToastSpawners();
        List<Bomb> bombList = currDungeon.getBombs();
        List<Entity> entityList = currDungeon.getEntityList();

        String dungeonId = currDungeon.getDungeonName() + Instant.now().getEpochSecond();

        // EXCEPTION CHECKS
        // If itemUsed is not a usable item or not null
        if (!(getUsableItems().contains(itemUsed)) && itemUsed != null) {
            throw new IllegalArgumentException("Item is not usable");
        }
        // If itemUsed is not in inventory
        if (currInventory.numberOfItem(itemUsed) == 0 && itemUsed != null) {
            throw new InvalidActionException("Item is not in the inventory");
        }

        if (!movementDirection.equals(Direction.NONE)) {
            player.move(movementDirection);
            for (MovingEntity enemy : enemyList) {
                enemy.move();
                if (player.getPosition().equals(enemy.getPosition())) {
                    if (enemy instanceof Mercenary) {
                        Mercenary mercenary = (Mercenary) enemy;
                        if (!mercenary.IsBribed()) {
                            while (player.battle(enemy)) {
                                // otherenemies.move();
                            }
                        }
                    } else {
                        player.battle(enemy);
                    }
                }
            }
        } else {
            switch (itemUsed) {
                case "bomb":
                    currInventory.breakItem("bomb");
                    Bomb bomb = new Bomb(player.getX(), player.getY());
                    entityList.add(bomb);
                    break;
                case "health_potion":
                    // If player health FULL, do not use
                    if (player.getHealth() == player.getMaxHealth()) {
                        break;
                    }
                    player.healToFullHealth();
                    currInventory.breakItem("health_potion");
                case "invincibility_potion":
                    player.consumeInvincibilityPotion();
                    break;
                case "invisibility_potion":
                    player.consumeInvisibilityPotion();
                    break;
                default:
                    break;
            }
        }

        // Attempt to spawn any ZombieToastSpawners
        for (ZombieToastSpawner spawner : spawnerList) {
            spawner.spawnZombieToast(currDungeon);
        }

        for (Bomb bomb : bombList) {
            Position bombPosition = bomb.getPosition();
            List<Entity> cardinallyAdjacentList = currDungeon.getEntitiesCardinallyAdjacent(bombPosition);
            for (Entity entity : cardinallyAdjacentList) {
                if (entity instanceof FloorSwitch) {
                    FloorSwitch floorSwitch = (FloorSwitch) entity;
                    if (floorSwitch.getIsActive()) {
                        bomb.explode(bomb);
                        break;
                    }
                }
            }
        }

        // Update
        player.updatePotionDuration();
        currDungeon.updateBuildableListBow();
        currDungeon.updateBuildableListShield();
        currDungeon.updateGoal();

        DungeonResponse response = new DungeonResponse(dungeonId, currDungeon.getDungeonName(), currDungeon.getEntityResponse(),
                                                        currDungeon.getItemResponse(), currDungeon.getBuildableString(), currDungeon.getGoalString());
        return response;
    }

    /**
     * Method for the player interacting with a mercenary or zombie toast spawner
     * @param entityId of entity
     * @return
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Dungeon currDungeon = Dungeon.getDungeon();
        DungeonResponse response = null;
        String dungeonId = currDungeon.getDungeonName() + Instant.now().getEpochSecond();
        Position playerPos = currDungeon.getPlayer().getPosition();
        List<Entity> entityCardinallyAdjacent = currDungeon.getEntitiesCardinallyAdjacent(playerPos);
        List<Entity> entities = currDungeon.getEntityList();
        List<String> entityIds = currDungeon.entityIdList();
        Inventory inventory = currDungeon.getInventory();

        // check whether the entityId is valid
        if (!entityIds.contains(entityId)) {
            throw new IllegalArgumentException("Incorrect interactable entity");
        } else if (entityId.contains("Mercenary")) {
            for (Entity entity : entities) {
                if (entity instanceof Mercenary) {
                    if (!currDungeon.checkBribeRange(entity)) {
                        throw new IllegalArgumentException("Not close enough to bribe");
                    } else if (currDungeon.checkBribeRange(entity)) {
                        // check if player has treasure
                        if (inventory.numberOfItem("treasure") < 1) {
                            throw new InvalidActionException("Cannot bribe without treasure");
                        } 
                        Mercenary mercenary = (Mercenary)entity;
                        mercenary.bribe();
                    }
                }
            }
        } else if (entityId.contains("ZombieToastSpawner")) {
            for (Entity entity : entityCardinallyAdjacent) {
                if (entity instanceof ZombieToastSpawner) {
                    // check if player has weapon
                    if (inventory.numberOfItem("sword") < 1 && inventory.numberOfItem("bow") < 1) {
                        throw new InvalidActionException("Cannot destroy zombie toast spawner without a weapon");
                    } else if (inventory.numberOfItem("sword") >=1 || inventory.numberOfItem("bow") >= 1) { 
                        entities.remove(entity);
                    }
                }
            }
        }
        currDungeon.updateGoal();
        response = new DungeonResponse(dungeonId, currDungeon.getDungeonName(), currDungeon.getEntityResponse(),
                                        currDungeon.getItemResponse(), currDungeon.getBuildableString(), currDungeon.getGoalString());
  
        return response;
    }

    /**
     * Method to create a buildable entity
     * @param buildable
     * @return
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        Dungeon currDungeon = Dungeon.getDungeon();
        DungeonResponse response = null;
        Boolean canBuildBow = currDungeon.updateBuildableListBow();
        Boolean canBuildShield = currDungeon.updateBuildableListShield();
        String dungeonId = currDungeon.getDungeonName() + Instant.now().getEpochSecond();
        Inventory currInventory = currDungeon.getInventory();

        if (!(buildable.equals("bow") || buildable.equals("shield"))) {
            throw new IllegalArgumentException("Incorrect buildable entity");
        }
        // check for InvalidActionException
        if (buildable.equals("bow") && !canBuildBow) {
            throw new InvalidActionException("Not enough ingredients to build this");
        } else if (buildable.equals("shield") && !canBuildShield) {
            throw new InvalidActionException("Not enough ingredients to build this");
        }

        if (canBuildBow && buildable.equals("bow")) {
            Bow bow = new Bow(-1, -1);
            bow.useIngredient();
            currDungeon.updateBuildableListBow();
            currInventory.addItem(bow);
            response = new DungeonResponse(dungeonId, currDungeon.getDungeonName(), currDungeon.getEntityResponse(),
                                            currDungeon.getItemResponse(), currDungeon.getBuildableString(), currDungeon.getGoalString());
        } else if (canBuildShield && buildable.equals("shield")) {
            Shield shield = new Shield(-1, -1);
            shield.useIngredient();
            currDungeon.updateBuildableListShield();
            currInventory.addItem(shield);
            response = new DungeonResponse(dungeonId, currDungeon.getDungeonName(), currDungeon.getEntityResponse(),
                                            currDungeon.getItemResponse(), currDungeon.getBuildableString(), currDungeon.getGoalString());
        }

        return response;
    }

    /**
     * USED FOR TESTING
     * Returns responseInfo for an entity
     * @param entityId
     * @return EntityResponse
     */
    public EntityResponse getInfo(String entityId) {
        return dungeon.getInfo(entityId);
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * USED FOR TESTING
     * Clears all save data
     */
    public void clearData() {
        for (String games : allGames()) {
            File gameFile = new File(savesPath + "\\" + games + ".json");
            gameFile.delete();
        }
    }

}