package dungeonmania.entities.staticEntity;

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

    public Portal(int x, int y, String colour) {
        super(x, y, "portal");
        setId("portal" + String.valueOf(counter));
        counter++;
        setPassable(isPassable);
        setColour(colour);
    }

    public void teleport(Entity entity, Position otherPortal) {
        Position newPos = otherPortal;
        if (entity instanceof Player || entity instanceof Mercenary) {
            entity.setPosition(newPos.getX(), newPos.getY());
        }
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

}
