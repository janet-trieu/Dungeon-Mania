package dungeonmania.entities.staticEntity;

public class Portal extends StaticEntity {
    
    private boolean isPassable = true;
    private String colour;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Portal(int x, int y, String colour) {
        super(x, y, "portal");
        setId("Portal" + String.valueOf(counter));
        counter++;
        setPassable(isPassable);
        setColour(colour);
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public static int getCounter() {
        return counter;
    }
}
