package dungeonmania.entities.collectableEntity;

public class Sword extends CollectableEntity implements Breakable {

    // durability attribute of sword is set to 6 (ticks)
    public final static int maxDurability = 6;
    public final static int damage = 2;
    private static int durability = maxDurability;

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
    }

    public static void setCounter(int counter) {
        Sword.counter = counter;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void setDurability(int durability) {
        Sword.durability = durability;
    }
    
}
