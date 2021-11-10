package dungeonmania.entities.movingEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.entities.staticEntity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {

    // Moving entities will always be in layer 3
    private int layer = 3;

    // storing the attribute of moving entity's health
    private double health;

    // storing the attribute of moving entity's damage
    private double damage;
   
    /**
     * Constructor for a moving entity
     * @param x position
     * @param y position
     * @param type of moving entity
     * @param health of moving entity
     * @param damage of moving entity
     */
    public MovingEntity(int x, int y, String type, int health, int damage) {
        super(x,y,type);
        this.health = health;
        this.damage = damage;
        setLayer(layer);
    }

    /**
     * Abstract method to simulate the movement of moving entities
     */
    public abstract void move();

    /**
     * Getter for health of moving entity
     * @return
     */

    public double getHealth() {return health;}

    /**
     * Setter for health of moving entity
     * @param health
     */
    public void setHealth(double health) {this.health = health;}

    /**
     * Getter for the damage of moving entity
     * @return
     */
    public double getDamage() {return damage;}

    /**
     * Setter for the damage of moving entity
     * @param damage
     */
    public void setDamage(double damage) {this.damage = damage;}

    /**
     * Sets damage to 0 if gamemode is peaceful
     * @param dungeon
     */
    public void setDamagePeaceful(Dungeon dungeon) {
        if (dungeon.getGameMode().equals("Peaceful")) {
            setDamage(0);
        }
    }

    public void move(Direction direction, Entity entity) {
        Dungeon dungeon = Dungeon.getDungeon();
        Position move = entity.getPosition().translateBy(direction);
        
        List<Entity> list = dungeon.getEntitiesOnSamePosition(move);
        
        for(Entity current : list) {
            if (current instanceof Boulder || current instanceof Wall || current instanceof Portal) {
                    return;
                }
            }
            entity.setPosition(move.getX(), move.getY());
    }

    /**
     * Returns list of distance between adjacent positions of entities and player's positions
     * @param entity
     * @param dungeon
     * @return
     */
    public List<Integer> Path(Entity entity, Dungeon dungeon) {
        Entity player = dungeon.getPlayer();
        Position up = entity.getPosition().translateBy(Direction.UP);
        Position down = entity.getPosition().translateBy(Direction.DOWN);
        Position left = entity.getPosition().translateBy(Direction.LEFT);
        Position right = entity.getPosition().translateBy(Direction.RIGHT);
        List<Position> movement = new ArrayList<>();
        movement.add(entity.getPosition());
        movement.add(up);
        movement.add(down);
        movement.add(left);
        movement.add(right); 

        List<Integer> distance = new ArrayList<>();

        for (Position curr : movement) {
            distance.add(Math.abs(curr.getX() - player.getX()) + Math.abs(curr.getY() - player.getY()));
        }

        return distance;
    }

    /**
     * Method for the moving entities to "run away" if player is invincible
     * @param entity
     * @param dungeon
     */
    public void run(Entity entity, Dungeon dungeon) {
        Player player = (Player) dungeon.getPlayer();
        List<Integer> distance = Path(entity, dungeon);
        Direction move = Direction.NONE;
        int index = distance.indexOf(Collections.max(distance));
        if(index == 0) {
            return;
        }
        if(index == 1) {
            move = Direction.UP;
        }
        if(index == 2) {
            move = Direction.DOWN;
        }
        if(index == 3) {
            move = Direction.LEFT;
        }
        if(index == 4) {
            move = Direction.RIGHT;
        }
        Position next = entity.getPosition().translateBy(move);
        
        List<Entity> list = dungeon.getEntitiesOnSamePosition(next);
        
        //check if entity runs into a wall or boulder;
        for(Entity current : list) {
            if (current instanceof Boulder) {
                if(entity instanceof Mercenary) {
                    player.interactBoulder(current, next, move);
                }
                if(entity instanceof Spider || entity instanceof ZombieToast) {
                    return;
                }
            }

            if (current instanceof Wall) {
                if(entity instanceof ZombieToast || entity instanceof Mercenary )
                return;
            }
        }

        entity.setX(next.getX());
        entity.setY(next.getY());
    }
    
    public void dijkstra(HashMap<Position, Integer> graph, ) {
        HashMap<Position, Double> dist = new HashMap<>();
        HashMap<Position, Position> prev = new HashMap<>();
        int a = infinit
        for(int i = 0; i < 50; i++) {
            for(int j = 0; j < 50; j++) {
                Position pos = new Position(j,i);
                dist.put(pos, Double.POSITIVE_INFINITY);
                
            }
        }

    }
    public HashMap<Position, Integer> Graph(Dungeon dungeon) {
        HashMap<Position, Integer> grid = new HashMap<>();
        int maxX;
        int maxY;
        Player player = (Player) dungeon.getPlayer();
        if (this.getX() > player.getX()) {
            maxX = this.getX() + 1;

        }
        else {
            maxX = player.getX() + 1;
        }
        
        if (this.getY() > player.getY()) {
            maxY = this.getY() + 1;
        }
        else {
            maxY = player.getY() + 1;
        }

        for(int i = 0; i < maxY; i++) {
            for(int j = 0; j < maxX; j++) {
                Position pos = new Position(j,i);
                int cost = 1;
                List<Entity> list = dungeon.getEntitiesOnSamePosition(pos);

                for(Entity current : list) {
                    if (current instanceof Wall) {
                        cost = 3;
                    }
                    if (current instanceof Boulder) {
                        cost = 2;
                    }
                    if (current instanceof SwampTile) {
                        SwampTile tile = (SwampTile) current;
                        cost = SwampTile.getFactor();
                    }
                }
                grid.put(pos, cost);

            }
        }
        return grid;
    }

}
