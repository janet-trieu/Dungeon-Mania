package dungeonmania.entities.movingEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.Inventory;
import dungeonmania.entities.collectableEntity.CollectableEntity;
import dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity.Sceptre;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.entities.staticEntity.SwampTile;
import dungeonmania.entities.staticEntity.Wall;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends BossEntity implements Bribeable {
    
    private static int counter = 0;

    // assassin is interactable with player ( for bribing )
    private boolean isInteractable = true;

    // assassin is spawned as hostile
    Boolean isBribed = false;

    // dungeon instance
    Dungeon dungeon;

    public Assassin(int x, int y, Dungeon dungeon) {
        super(x, y, "assassin", 30, 5); 
        setId("Assassin" + String.valueOf(counter));
        setIsInteractable(isInteractable);
        this.dungeon = dungeon;
        counter++;
    }

    @Override
    public void takeDamagePlayer(Player player) {
        if (player.isAnduril()) {
            super.takeDamage(player.getHealth(), player.getDamage() * 3);
        } else {
            super.takeDamage(player.getHealth(), player.getDamage());
        }
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Assassin.counter = counter;
    }

    /**
     * Method to bribe the assassin with the one ring and treasure to become an ally 
     */
    @Override
    public void bribe() {
        Inventory inventory = dungeon.getInventory();
        if (inventory.numberOfItem("sceptre") > 0) {
            CollectableEntity item = inventory.invGetInstance("sceptre");
            Sceptre sceptre = (Sceptre)item;
            sceptre.mindControl(this);
            return;
        }
        // if player has sun stone and the one ring, use to bribe assassin
        // one ring is used up, sun stone remains
        if (inventory.numberOfItem("sun_stone") > 0 && inventory.numberOfItem("the_one_ring") > 0) {
            setIsBribed(true);
            inventory.breakItem("one_ring");
            return;

        // else, player uses treasure and the one ring to bribe assassin    
        } else if (inventory.numberOfItem("treasure") > 0 && inventory.numberOfItem("the_one_ring") > 0) {
            setIsBribed(true);
            inventory.breakItem("treasure");
            inventory.breakItem("one_ring");
            return;

        // player cannot bribe assassin    
        } else {
            throw new InvalidActionException("Cannot bribe without treasure or sun stone and one ring");
        }
    }
    
    @Override
    public void move() {
        if(this.getDebuff() > 0) {
            setDebuff(this.getDebuff() - 1);
            return;
        }
        Player player = (Player) dungeon.getPlayer();
        if (player.isInvincible()) {
            if(!isBribed) {
            run(this, dungeon);
            }
        }

        List<Integer> distance = Path(this, dungeon);

        int index = distance.indexOf(Collections.min(distance));
        int mindist = distance.get(index);
        List<Direction> min = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            if(distance.get(i) == mindist) {
                if (i == 0) {return;}
                if (i == 1) {min.add(Direction.UP);}
                if (i == 2) {min.add(Direction.DOWN);}
                if (i == 3) {min.add(Direction.LEFT);}
                if (i == 4) {min.add(Direction.RIGHT);}
            }
        }
       
        
        int a = 0;
        Position next = this.getPosition().translateBy(min.get(a));

        // if Position to move to is wall, do nothing;
        List<Entity> list = dungeon.getEntitiesOnSamePosition(next);
        for(Entity current : list) {
            if (current instanceof Boulder) {
                player.interactBoulder(current, next, min.get(a));
                break;
            }
            if (current instanceof Wall) {
                if(min.size() == 1) {
                    return;
                }
                a++;
                next = this.getPosition().translateBy(min.get(a));
            }
            if (current instanceof Player) {
                if (min.size() == 1) {
                    return;
                }
                if (!isBribed()) {
                    break;
                }
                a++;
                next = this.getPosition().translateBy(min.get(a));
            }

            if (current instanceof Portal) {
                Portal portal = (Portal) current;
                next = portal.correspondingPortalPosition().translateBy(min.get(a));

                List<Entity> lis = dungeon.getEntitiesOnSamePosition(next);
                for (Entity spot : lis) {
                    if(spot instanceof Wall) {
                        return;
                    }
                    if (spot instanceof Boulder) {
                        player.interactBoulder(spot, next, min.get(a));
                        break;
                    }
                }
            }
            if (current instanceof SwampTile) {
                SwampTile tile = (SwampTile) current;
                setDebuff(tile.getMovementFactor() - 1);
            }
        }
        this.setX(next.getX());
        this.setY(next.getY());
    }

    @Override
    public boolean isBribed() {
        return isBribed;
    }

    @Override
    public void setIsBribed(boolean isBribed) {
        this.isBribed = isBribed;
    }
    
}
