package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

public class Bow extends BuildableEntity {

    // durability attribute of bow is set to 3 (ticks)
    private int durability = 3;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a bow
     * @param x position
     * @param y position 
     */
    public Bow(int x, int y) {
        super(x, y, "bow");
        setId("Bow" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }
    
}
