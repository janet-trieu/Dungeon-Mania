package dungeonmania.entities.collectableEntity.breakableEntity;

public class Sword extends BreakableEntity {

    // durability attribute of sword is set to 6 (ticks)
    private int durability = 6;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a sword
     * @param x position
     * @param y position
     */
    public Sword(int x, int y) {
        super(x, y, "sword");
        setId("Sword" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }
    
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Sword.counter = counter;
    }

}
