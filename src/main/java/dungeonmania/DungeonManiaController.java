package dungeonmania;

import dungeonmania.goals.*;
import dungeonmania.entities.*;
import dungeonmania.entities.staticEntity.*;
import dungeonmania.entities.movingEntity.*;
import dungeonmania.entities.collectableEntity.*;
import dungeonmania.entities.collectableEntity.rareCollectableEntity.*;
import dungeonmania.entities.collectableEntity.potionEntity.*;
import dungeonmania.entities.collectableEntity.buildableEntity.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntity.Bribeable;

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

import org.json.JSONArray;
import org.json.JSONObject;

public class DungeonManiaController {
    private Dungeon dungeon;

    public DungeonManiaController() {
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("standard", "peaceful", "hard");
    }

    /**
     * Return a list of entity ids that are usable items 
     * e.g. bomb, and all the potions
     * @return
     */
    public List<String> getUsableItems() {
        List<CollectableEntity> inventory = dungeon.getInventory().getItems();
        List<String> canUseEntityIds = new ArrayList<String>();
        for (CollectableEntity entity : inventory) {
            if (entity instanceof Bomb || entity instanceof HealthPotion || 
                entity instanceof InvisibilityPotion || entity instanceof InvincibilityPotion) {

                canUseEntityIds.add(entity.getId());
            }
        }
        return canUseEntityIds;
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

        // EXCEPTION CHECKS //
        // If dungeonName does not exist
        if (!maps.contains(dungeonName)) {
            throw new IllegalArgumentException("Invalid dungeonName");
        }
        // If gameMode is invalid
        if (!getGameModes().contains(gameMode)) {
            throw new IllegalArgumentException("Invalid gameMode");
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

        // Set spawnPoint
        Position spawnPoint = dungeon.getPlayer().getPosition();
        dungeon.setSpawnPoint(spawnPoint);
        
        // Add "goals"
        if (mapObj.has("goal-condition")) {
            JSONObject goalObj = mapObj.getJSONObject("goal-condition");
            Goal goal = createGoal(goalObj, dungeon);
            dungeon.setGoal(goal);
            return new DungeonResponse(dungeonId, dungeonName, dungeon.getEntityResponse(), dungeon.getItemResponse(),
                                        dungeon.getBuildableString(), dungeon.getGoalString());
        }

        Goal goal = new ExitGoal(dungeon);
        dungeon.setGoal(goal);

        // For maps that do not have goals; make the goal 'exit'
        return new DungeonResponse(dungeonId, dungeonName, dungeon.getEntityResponse(), dungeon.getItemResponse(),
                                    dungeon.getBuildableString(), dungeon.getGoalString());
    }

    /**
     * Calls create entity to create entity, then adds to dungeon
     * @param mapEntities
     */
    private void addEntitiesDungeon(JSONArray mapEntities) {
        for (int i = 0; i < mapEntities.length(); i++) {
            JSONObject obj = mapEntities.getJSONObject(i);
            Entity entity = createEntity(obj);
            dungeon.addEntity(entity);
        }
    }

    /**
     * Given a JSONObject, it creates the entity given the object's info. and returns it
     * @param mapObj
     */
    private Entity createEntity(JSONObject obj) {
        int x = -1;
        int y = -1;
        String type = "";
        int keyId = -1;
        
        if (obj.has("x")) { x = obj.getInt("x"); }
        if (obj.has("y")) { y = obj.getInt("y"); }
        if (obj.has("type")) { type = obj.getString("type"); }
        if (obj.has("key")) { keyId = obj.getInt("key"); }

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
                if (obj.has("hasArmour")) { mercenary.setHasArmour(obj.getBoolean("hasArmour")); }
                if (obj.has("isBribed")) { mercenary.setIsBribed(obj.getBoolean("isBribed")); }
                if (obj.has("tickCounter")) { Mercenary.setTickCounter(obj.getInt("tickCounter")); }
                return mercenary;
            case "player":
                Player player = new Player(x, y);
                if (obj.has("health")) {
                    player.setHealth(obj.getInt("health"));
                    int invinDur = obj.getInt("invincibilityDuration");
                    int invisDur = obj.getInt("invisibilityDuration");
                    int bowDur = obj.getInt("bowDurability");
                    int armourDur = obj.getInt("armourDurability");
                    int swordDur = obj.getInt("swordDurability");
                    int shieldDur = obj.getInt("shieldDurability");
                    player.setPlayerStates(invinDur, invisDur, bowDur, armourDur, swordDur, shieldDur);
                }
                return player;
            case "portal":
                String colour = "";
                if (obj.has("colour")) { colour = obj.getString("colour"); }
                Portal portal = new Portal(x, y, colour);
                return portal;
            case "shield":
                Shield shield = new Shield(x, y);
                return shield;
            case "spider":
                Spider spider = new Spider(x, y, dungeon);
                if (obj.has("tickCounter")) { Spider.setTickCounter(obj.getInt("tickCounter")); }
                if (obj.has("spiderNum")) { Spider.setSpiderNum(obj.getInt("spiderNum")); }
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
                if (obj.has("hasArmour")) { zombieToast.setHasArmour(obj.getBoolean("hasArmour")); }
                return zombieToast;
            case "zombie_toast_spawner":
                ZombieToastSpawner zombieToastSpawner = new ZombieToastSpawner(x, y);
                if (obj.has("tickCounter")) { ZombieToastSpawner.setTickCounter(obj.getInt("tickCounter")); }
                return zombieToastSpawner;
            case "sun_stone":
                SunStone sunStone = new SunStone(x, y);
                return sunStone;
            case "sceptre":
                Sceptre sceptre = new Sceptre(x, y);
                return sceptre;
            case "midnight_armour":
                MidnightArmour midnightArmour = new MidnightArmour(x, y);
                return midnightArmour;
            case "assassin":
                Assassin assassin = new Assassin(x ,y, dungeon);
                return assassin;
            case "hydra":
                Hydra hydra = new Hydra(x, y, dungeon);
                if (obj.has("tickCounter")) { Hydra.setTickCounter(obj.getInt("tickCounter")); }
                return hydra;
            case "swamp_tile":
                int movementFactor = -1;
                if (obj.has("movement_factor")) { movementFactor = obj.getInt("movement_factor"); }
                SwampTile swampTile = new SwampTile(x, y, movementFactor);
                return swampTile;
            case "anduril":
                Anduril anduril = new Anduril(x, y);
                return anduril;
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
            // Recursively create subgoals
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
        try {
            Files.createDirectories(Paths.get("savedGames"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Dungeon currDungeon = Dungeon.getDungeon();
        String dungeonId = currDungeon.getDungeonName() + Instant.now().getEpochSecond();

        JSONObject saveObj = new JSONObject();

        // General game info. saving
        saveObj.put("filename", name);
        saveObj.put("gameMode", currDungeon.getGameMode());
        saveObj.put("dungeonId", dungeonId);
        saveObj.put("dungeonName", currDungeon.getDungeonName());
        saveObj.put("goals", currDungeon.getGoalString());
        
        // Entity saving
        saveObj.put("entities", saveEntities());

        // Inventory saving
        saveObj.put("inventory", saveInventory());

        // Buildable saving
        saveObj.put("buildables", saveBuilable());

        // SpawnPoint saving
        Position spawnPoint = currDungeon.getSpawnPoint();
        JSONObject spawnObj = new JSONObject();
        spawnObj.put("x", spawnPoint.getX());
        spawnObj.put("y", spawnPoint.getY());
        saveObj.put("spawnPoint", spawnObj);

        // Insert object into file
         try {
            FileWriter writer = new FileWriter("savedGames/" + name + ".json");
            writer.write(saveObj.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println("Data creation error in saveGame: " + e.getMessage());
        }

        DungeonResponse response = new DungeonResponse(dungeonId, currDungeon.getDungeonName(), currDungeon.getEntityResponse(),
                                                    currDungeon.getItemResponse(), currDungeon.getBuildableString(), currDungeon.getGoalString());

        return response;
    }

    /**
     * Loops through current dungeon entity list and returns it as a JSONArray
     * @return JSONArray of all dungeon's entities
     */
    private JSONArray saveEntities() {
        Dungeon currDungeon = Dungeon.getDungeon();
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
                entityInfo.put("isBribed", mercenary.isBribed());
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
            } else if (entity instanceof SwampTile) {
                SwampTile swampTile = (SwampTile) entity;
                entityInfo.put("movementFactor", swampTile.getMovementFactor());
            } else if (entity instanceof Spider) {
                entityInfo.put("SpiderNum", Spider.getSpiderNum());
                entityInfo.put("tickCounter", Spider.getTickCounter());
            } else if (entity instanceof Hydra) {
                entityInfo.put("tickCounter", Hydra.getTickCounter());
            }
            entityArray.put(entityInfo);
        }
        return entityArray;
    }

    /**
     * Loops through current dungeon inventory and returns it as a JSONArray
     * @return JSONArray of current inventory
     */
    private JSONArray saveInventory() {
        Dungeon currDungeon = Dungeon.getDungeon();
        JSONArray inventoryArray = new JSONArray();
        Inventory inventory = currDungeon.getInventory();

        for (CollectableEntity item : inventory.getItems()) {
            JSONObject itemInfo = new JSONObject();
            itemInfo.put("id", item.getId());
            itemInfo.put("type", item.getType());
            inventoryArray.put(itemInfo);
        }
        return inventoryArray;
    }

    /**
     * Loops through current dungeon buildable list and returns it as a JSONArray
     * @return JSONArray of current buildable items
     */
    private JSONArray saveBuilable() {
        Dungeon currDungeon = Dungeon.getDungeon();
        JSONArray buildableArray = new JSONArray();
        for (String item : currDungeon.getBuildableString()) {
            buildableArray.put(item);
        }
        return buildableArray;
    }

    /**
     * Loads game
     * @param name
     * @return DungeonResponse of selected dungeon
     * @throws IllegalArgumentException
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        dungeon = new Dungeon();

        // If '...savedGames' doesn't exist, create it (automatically creates in bin file as well)
        try {
            Files.createDirectories(Paths.get("savedGames"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        File gameFile = new File("savedGames/" + name + ".json");

        // EXCEPTION CHECKING
        // If filename does not exist/valid
        if (!gameFile.exists()) {
            throw new IllegalArgumentException("Name is not a valid game name");
        }

        // Read gameMode and dungeonResponse data
        try {
            FileLoader.listFileNamesInDirectoryOutsideOfResources("savedGames/" + name + ".json");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Path savesPath = Paths.get("savedGames/" + name + ".json");
        String data = "";
        try {
            data = Files.readString(savesPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject dataObj = new JSONObject(data);

        // Set general dungeon info
        String dungeonName = dataObj.getString("dungeonName");
        String gameMode = dataObj.getString("gameMode");
        String dungeonId = dataObj.getString("dungeonId");
        dungeon.setDungeonName(dungeonName);
        dungeon.setGameMode(gameMode);
        dungeon.setDungeonId(dungeonId);
        
        // Add entities and set necessary attributes, placing in Dungeon
        JSONArray mapEntities = dataObj.getJSONArray("entities");
        addEntitiesDungeon(mapEntities);
        
        // Inventory loading
        dungeon.setInventory(loadInventory(dataObj));
        
        // Buildable loading
        dungeon.setBuildableList(loadBuilable(dataObj));

        // Goal loading
        Goal goal = loadGoal(dataObj.getString("goals"), dungeon);
        dungeon.setGoal(goal);

        // SpawnPoint loading
        JSONObject spawnObj = dataObj.getJSONObject("spawnPoint");
        Position spawnPoint = new Position(spawnObj.getInt("x"), spawnObj.getInt("y"));
        dungeon.setSpawnPoint(spawnPoint);

        // Create new dungeonResponse from dungeon class to ensure its correct and return
        DungeonResponse response = new DungeonResponse(dungeonId, dungeon.getDungeonName(), dungeon.getEntityResponse(),
                                                    dungeon.getItemResponse(), dungeon.getBuildableString(), dungeon.getGoalString());

        return response;
    }

    /**
     * Loads the inventory
     * @param dataObj
     * @return loaded inventory
     */
    private Inventory loadInventory(JSONObject dataObj) {
        Inventory inventory = new Inventory();
        JSONArray inventoryJSON = dataObj.getJSONArray("inventory");
        for (int i = 0; i < inventoryJSON.length(); i++) {
            JSONObject itemObj = inventoryJSON.getJSONObject(i);
            CollectableEntity item = (CollectableEntity) createEntity(itemObj);
            inventory.addItem(item);
        }
        return inventory;
    }

    /**
     * Loads buildables
     * @param dataObj
     * @return list of all buildable items
     */
    private List<String> loadBuilable(JSONObject dataObj) {
        List<String> buildable = new ArrayList<String>();
        JSONArray buildableJSONArray = dataObj.getJSONArray("buildables");
        for (int i = 0; i < buildableJSONArray.length(); i++) {
            String buildableType = (String) buildableJSONArray.get(i);
            buildable.add(buildableType);
        }
        return buildable;
    }

    /**
     * Loads a goal from a string
     * @param goal
     * @param dungeon
     * @return
     */
    private Goal loadGoal(String goal, Dungeon dungeon) {
        if (goal.contains("(")) {
            CompositeGoal compositeGoal;
            if (goal.contains("AND")) {
                compositeGoal = new AndGoal(dungeon);
            } else {
                compositeGoal = new OrGoal(dungeon);
            }

            //TOKENISE
            goal = goal.replaceAll("([(,:,),AND,OR])", "");
            String[] goals = goal.split(" ");

            for (int i = 0; i < goals.length; i++) {
                if (goals[i].equals("")) {
                    continue;
                }
                Goal leafGoal = loadGoal(goals[i], dungeon);
                compositeGoal.addSubGoal(leafGoal);
            }
            return compositeGoal;
        }

        if (goal.contains("exit")) {
            return new ExitGoal(dungeon);
        } else if (goal.contains("enemy")) {
            return new EnemyGoal(dungeon);
        } else if (goal.contains("switch")) {
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
        List<String> maps = new ArrayList<String>();
        if (!Files.exists(Paths.get("savedGames"))) {
            return maps;
        }

        try {
            maps = FileLoader.listFileNamesInDirectoryOutsideOfResources("savedGames");
        } catch (IOException e) {
            System.err.println("Error in loading file names: " + e.getMessage());
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

        if (player == null) {
            return getDungeonResponse();
        }

        // EXCEPTION CHECKS
        // If itemUsed is not a usable item or not null
        if (!(getUsableItems().contains(itemUsed)) && itemUsed != null) {
            throw new IllegalArgumentException("Item is not usable");
        }

        // If itemUsed is not in inventory
        String type = null;
        Boolean hasItem = false;
        for (CollectableEntity item : currInventory.getItems()) {
            if (item.getId().equals(itemUsed)) {
                hasItem = true;
                type = item.getType();
                break;
            }
        }
        if (hasItem == false && itemUsed != null) {
            throw new InvalidActionException("Item is not in the inventory");
        }

        // Moving
        if (!movementDirection.equals(Direction.NONE)) {
            player.move(movementDirection);
            
            // tick implementation for battle + enemy movement
            for (MovingEntity enemy : enemyList) {
                if (player.getPosition().equals(enemy.getPosition())) {
                    if (enemy instanceof Bribeable) {
                        Bribeable mercAss = (Bribeable) enemy;
                        if (!mercAss.isBribed()) {
                            while (player.battle(enemy)) {
                            }
                            if (player.getHealth() <= 0) {
                                return getDungeonResponse();
                            }
                        }
                    } else {
                        while(player.battle(enemy)) {
                        }
                        if (player.getHealth() <= 0) {
                            return getDungeonResponse();
                        }
                    }
                }
            }
        }
        
        // Item use
        if (itemUsed != null) {
            switch (type) {
                case "bomb":
                    Bomb bomb = new Bomb(player.getX(), player.getY());
                    currInventory.breakItem("bomb");
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

        // enemy moves into player -> battle
        for (MovingEntity enemy : enemyList) {
            enemy.move();
            if (player.getPosition().equals(enemy.getPosition())) {
                if (enemy instanceof Bribeable) {
                    Bribeable mercenary = (Bribeable) enemy;
                    if (!mercenary.isBribed()) {
                        while (player.battle(enemy)) {
                        }
                        if (player.getHealth() <= 0) {
                            return getDungeonResponse();
                        }
                    }
                } else {
                    while(player.battle(enemy)) {
                    }
                    if (player.getHealth() <= 0) {
                        return getDungeonResponse();
                    }
                }
            }
        }

        // Attempt to spawn any ZombieToastSpawners
        for (ZombieToastSpawner spawner : spawnerList) {
            spawner.spawnZombieToast(currDungeon);
        }

        // Attempt to spawn any Spiders
        Position spawnPos = currDungeon.spawnDistance(player);
        Spider spider = new Spider(spawnPos.getX(), spawnPos.getY(), dungeon);
        spider.spawnSpider();

        // Attempt to spawn mercenary or assassin
        Position spawnPoint = currDungeon.getSpawnPoint();
        Mercenary mercenary = new Mercenary(spawnPoint.getX(), spawnPoint.getY(), dungeon);
        mercenary.spawnMercenary();

        if (currDungeon.getGameMode().equals("hard")) {
            Position hydraPos = currDungeon.spawnDistance(player);
            Hydra hydra = new Hydra(hydraPos.getX(), hydraPos.getY(), dungeon);
            hydra.spawnHydra();
        }


        // check the floor switch's isActive and position, as well as bomb position
        // Case 1: bomb is placed and a boulder activates switch
        for (Bomb bomb : bombList) {
            List<Entity> cardinallyAdjaceEntities = currDungeon.getEntitiesCardinallyAdjacent(bomb.getPosition());
            for (Entity entity : cardinallyAdjaceEntities) {
                if (entity instanceof FloorSwitch) {
                    FloorSwitch floorSwitch = (FloorSwitch) entity;
                    if (floorSwitch.getIsActive()) {
                        bomb.explode();
                    }
                }
            }
        }

        // update any mercenaries or assassins that are under mind control 
        List<Bribeable> mindControlledEntities = currDungeon.mindControlledEntities();
        for (Bribeable entity : mindControlledEntities) {
            entity.setMindControlDuration(entity.getMindControlDuration() - 1);
            entity.updateMindControl();
        }
        return getDungeonResponse();
    }

    /**
     * Method for returning current dungeon Response
     */
    public DungeonResponse getDungeonResponse() {
        Dungeon currDungeon = Dungeon.getDungeon();
        Player player = (Player) currDungeon.getPlayer();
        String dungeonId = currDungeon.getDungeonName() + Instant.now().getEpochSecond();

        // Update
        if (player != null) {player.updatePotionDuration();}
        currDungeon.updateGoal();
        currDungeon.updateBuildableListBow();
        currDungeon.updateBuildableListShield();
        currDungeon.updateBuildableListSceptre();
        currDungeon.updateBuildableListMidnightArmour();

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
        Position playerPos = currDungeon.getPlayer().getPosition();
        List<Entity> entityCardinallyAdjacent = currDungeon.getEntitiesCardinallyAdjacent(playerPos);
        List<Entity> entities = currDungeon.getEntityList();
        List<String> entityIds = currDungeon.entityIdList();
        Inventory inventory = currDungeon.getInventory();

        // check whether the entityId is valid
        if (!entityIds.contains(entityId)) {
            throw new IllegalArgumentException("Incorrect interactable entity");
        } else if (entityId.contains("Mercenary") || entityId.contains("Assassin")) {
            for (Entity entity : entities) {
                if (entity instanceof Bribeable && entity.getId().equals(entityId)) {
                    if (!currDungeon.checkBribeRange(entity)) {
                        throw new InvalidActionException("Not close enough to bribe");
                    } else if (currDungeon.checkBribeRange(entity)) {
                        Bribeable bribeableEntity = (Bribeable)entity;
                        bribeableEntity.bribe();
                    }
                }
            }
        } else if (entityId.contains("ZombieToastSpawner")) {
            for (Entity entity : entityCardinallyAdjacent) {
                if (entity instanceof ZombieToastSpawner) {
                    // check if player has weapon
                    if (inventory.numberOfItem("sword") < 1 && inventory.numberOfItem("bow") < 1 && 
                        inventory.numberOfItem("anduril") < 1 && inventory.numberOfItem("midnight_armour") < 1 ) {
                        throw new InvalidActionException("Cannot destroy zombie toast spawner without a weapon");
                    } else { 
                        entities.remove(entity);
                    }
                }
            }
        }
        return getDungeonResponse();
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
        Boolean canBuildBow = currDungeon.updateBuildableListBow();
        Boolean canBuildShield = currDungeon.updateBuildableListShield();
        Boolean canBuildSceptre = currDungeon.updateBuildableListSceptre();
        Boolean canBuildMidnightArmour = currDungeon.updateBuildableListMidnightArmour();
        Inventory currInventory = currDungeon.getInventory();

        if (!buildable.equals("bow") && !buildable.equals("shield") && 
            !buildable.equals("sceptre") && !buildable.equals("midnight_armour")) {
        // if (!(buildable.equals("bow")) || !(buildable.equals("shield")) || !(buildable.equals("sceptre"))) {
            throw new IllegalArgumentException("Incorrect buildable entity");
        }
        // check for InvalidActionException
        if (buildable.equals("bow") && !canBuildBow) {
            throw new InvalidActionException("Not enough ingredients to build bow");
        } else if (buildable.equals("shield") && !canBuildShield) {
            throw new InvalidActionException("Not enough ingredients to build shield");
        } else if (buildable.equals("sceptre") && !canBuildSceptre) {
            throw new InvalidActionException("Not enough ingredients to build sceptre");
        } else if (buildable.equals("midnight_armour") && !canBuildMidnightArmour) {
            throw new InvalidActionException("Not enough ingredients to build midnight armour");
        }

        if (canBuildBow && buildable.equals("bow")) {
            Bow bow = new Bow(-1, -1);
            bow.useIngredient();
            currDungeon.updateBuildableListBow();
            currInventory.addItem(bow);
        } else if (canBuildShield && buildable.equals("shield")) {
            Shield shield = new Shield(-1, -1);
            shield.useIngredient();
            currDungeon.updateBuildableListShield();
            currInventory.addItem(shield);
        } else if (canBuildSceptre && buildable.equals("sceptre")) {
            Sceptre sceptre = new Sceptre(-1, -1);
            sceptre.useIngredient();
            currDungeon.updateBuildableListSceptre();
            currInventory.addItem(sceptre);
        } else if (canBuildMidnightArmour && buildable.equals("midnight_armour")) {
            MidnightArmour midnightArmour = new MidnightArmour(-1, -1);
            midnightArmour.useIngredient();
            currDungeon.updateBuildableListMidnightArmour();
            currInventory.addItem(midnightArmour);
        }

        return getDungeonResponse();
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
            File gameFile = new File("savedGames/" + games + ".json");
            gameFile.delete();
        }
    }
    
}