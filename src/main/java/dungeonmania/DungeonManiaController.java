package dungeonmania;

import dungeonmania.goals.*;
import dungeonmania.entities.*;
import dungeonmania.entities.staticEntity.*;
import dungeonmania.entities.PotionState.*;
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

import java.io.IOException;
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
        return Arrays.asList("Standard", "Peaceful", "Hard");
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
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException, IOException {
        // EXCEPTION CHECKS //
        // If dungeonName does not exist
        List<String> maps = FileLoader.listFileNamesInResourceDirectory("dungeons");
        if (!maps.contains(dungeonName)) {
            throw new IllegalArgumentException("Invalid dungeonName");
        }
        // If gameMode is invalid
        if (!getGameModes().contains(gameMode)) {
            throw new IllegalArgumentException("Invalid gameMode");
        }

        // Initialise dungeon
        dungeon = new Dungeon(dungeonName);

        // Load the map
        String map = FileLoader.loadResourceFile("dungeons/" + dungeonName +".json");
        JSONObject mapObj = new JSONObject(map);

        // Add "entities"
        if (mapObj.has("entities")) {
            createEntities(mapObj);
        }
        // Add "goals"
        if (mapObj.has("goal-condition")) {
            createGoals(mapObj);
        }

        // Generate dungeonId
        String dungeonId = dungeonName + Instant.now().getEpochSecond();

        return new DungeonResponse(dungeonId, dungeonName, dungeon.getEntityResponse(), dungeon.getItemResponse(), dungeon.getBuildableString(), dungeon.getGoalString());
    }

    /**
     * Loop through JSON "entities" and adds each data to the Dungeon class
     * @param mapObj
     */
    private void createEntities(JSONObject mapObj) {
        JSONArray mapEntities = mapObj.getJSONArray("entities");

        for (int i = 0; i < mapEntities.length(); i++) {
            JSONObject obj = mapEntities.getJSONObject(i);
            int x = obj.getInt("x");
            int y = obj.getInt("y");
            String type = obj.getString("type");
            int keyId;
            String colour;
            
            if (obj.has("key")) {
                keyId = obj.getInt("key");
            }
            if (obj.has("colour")) {
                colour = obj.getString("colour");
            }

            switch (type) {
                case "armour":
                    Armour armour = new Armour(x, y);
                    dungeon.addEntity(armour);
                    break;
                case "arrow":
                    Arrow arrow = new Arrow(x, y);
                    dungeon.addEntity(arrow);
                    break;
                case "bomb":
                    Bomb bomb = new Bomb(x, y);
                    dungeon.addEntity(bomb);
                    break;
                case "boulder":
                    Boulder boulder = new Boulder(x, y);
                    dungeon.addEntity(boulder);
                    break;
                case "bow":
                    Bow bow = new Bow(x, y);
                    dungeon.addEntity(bow);
                    break;
                case "door_closed":
                    Door doorClosed = new Door(x, y, keyId);
                    dungeon.addEntity(doorClosed);
                    break;
                case "door_open":
                    Door doorOpen = new Door(x, y, true);
                    dungeon.addEntity(doorOpen);
                    break;
                case "exit":
                    Exit exit = new Exit(x, y);
                    dungeon.addEntity(exit);
                    break;
                case "health_potion":
                    HealthPotion healthPotion = new HealthPotion(x, y);
                    dungeon.addEntity(healthPotion);
                    break;
                case "invincibility_potion":
                    InvincibilityPotion invincibilityPotion = new InvincibilityPotion(x, y);
                    dungeon.addEntity(invincibilityPotion);
                    break;
                case "invisibility_potion":
                    InvisibilityPotion invisibilityPotion = new InvisibilityPotion(x, y);
                    dungeon.addEntity(invisibilityPotion);
                    break;
                case "key":
                    Key key = new Key(x, y, keyId);
                    dungeon.addEntity(key);
                    break;
                case "mercenary":
                    Mercenary mercenary = new Mercenary(x, y);
                    dungeon.addEntity(mercenary);
                    break;
                case "player":
                    Player player = new Player(x, y);
                    dungeon.addEntity(player);
                    break;
                case "portal":
                    Portal portal = new Portal(x, y, colour);
                    dungeon.addEntity(portal);
                    break;
                case "shield":
                    Shield shield = new Shield(x, y);
                    dungeon.addEntity(shield);
                    break;
                case "spider":
                    Spider spider = new Spider(x, y);
                    dungeon.addEntity(spider);
                    break;
                case "switch":
                    FloorSwitch floorSwitch = new FloorSwitch(x, y);
                    dungeon.addEntity(floorSwitch);
                    break;
                case "sword":
                    Sword sword = new Sword(x, y);
                    dungeon.addEntity(sword);
                    break;
                case "the_one_ring":
                    TheOneRing oneRing = new TheOneRing(x, y);
                    dungeon.addEntity(oneRing);
                    break;
                case "treasure":
                    Treasure treasure = new Treasure(x, y);
                    dungeon.addEntity(treasure);
                    break;
                case "wall":
                    Wall wall = new Wall(x, y);
                    dungeon.addEntity(wall);
                    break;
                case "wood":
                    Wood wood = new Wood(x, y);
                    dungeon.addEntity(wood);
                    break;
                case "zombie_toast":
                    ZombieToast zombieToast = new ZombieToast(x, y);
                    dungeon.addEntity(zombieToast);
                    break;
                case "zombie_toast_spawner":
                    ZombieToastSpawner zombieToastSpawner = new ZombieToastSpawner(x, y);
                    dungeon.addEntity(zombieToastSpawner);
                    break;
                default:
                    break;
            }
        }
    }
    
    /**
     * Loop through JSON "goal-condition" and "subgoals" (if exists) and adds data to the Dungeon class
     * @param mapObj
     */
    private void createGoals(JSONObject mapObj) {
        JSONObject goalCondition = mapObj.getJSONObject("goal-condition");
        String goal = goalCondition.getString("goal");
        JSONArray subgoals = goalCondition.getJSONArray("subgoals");

        switch (goal) {
            case "AND":
                AndGoal and = new AndGoal();
                for (int i = 0; i < subgoals.length(); i++) {
                    String subgoal = subgoals.getJSONObject(i).getString("goal");
                    and.addSubgoal(subgoal);
                }
                dungeon.addGoal(and);
                break;
            case "OR":
                OrGoal or = new OrGoal();
                for (int i = 0; i < subgoals.length(); i++) {
                    String subgoal = subgoals.getJSONObject(i).getString("goal");
                    or.addSubgoal(subgoal);
                }
                dungeon.addGoal(or);
                break;
            case "exit":
                ExitGoal exit = new ExitGoal();
                dungeon.addGoal(exit);
                break;
            case "enemies":
                EnemyGoal enemy = new EnemyGoal();
                dungeon.addGoal(enemy);
                break;
            case "boulders":
                SwitchGoal boulders = new SwitchGoal();
                dungeon.addGoal(boulders);
                break;
            case "treasure":
                TreasureGoal treasure = new TreasureGoal();
                dungeon.addGoal(treasure);
                break;
            default:
                break;
        }
    }

    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        return null;
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        return null;
    }

    public List<String> allGames() {
        return new ArrayList<>();
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    public EntityResponse getInfo(String entityId) {
        return dungeon.getInfo(entityId);
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void clearData() {
    }
}