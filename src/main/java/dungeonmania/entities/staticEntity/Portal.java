package dungeonmania.entities.staticEntity;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    
    // portal is passable for a player
    private boolean isPassable = true;

    // attribute to create a corresponding pair of portals by their colour
    private String colour;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a portal 
     * @param x position
     * @param y position
     * @param colour of portal
     */
    public Portal(int x, int y, String colour) {
        super(x, y, "portal");
        setId("Portal" + String.valueOf(counter));
        counter++;
        setPassable(isPassable);
        setColour(colour);
    }

    /**
     * Method that allows player and mercenary to teleport to the corresponding portal
     * @param entity
     * @param otherPortal
     */
    public void teleport(Entity entity, Position otherPortal) {
        Position newPos = otherPortal;
        if (entity instanceof Player || entity instanceof Mercenary) {
            entity.setPosition(newPos.getX(), newPos.getY());
        }
    }

    public Position correspondingPortalPosition() {
        List<Entity> entityList = Dungeon.getDungeon().getEntityList();
        for (Entity entity : entityList) {
            if (entity instanceof Portal && entity != this) {
                Portal correspondingPortal = (Portal) entity;
                if (correspondingPortal.getColour().equals(getColour())) {
                    return correspondingPortal.getPosition();
                }
            } 
        }
        return null;
    }

    /**
     * Getter for the colour of portal
     * @return
     */
    public String getColour() {
        return colour;
    }

    /**
     * Setter for the colour of portal
     * @param colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Portal.counter = counter;
    }
}
