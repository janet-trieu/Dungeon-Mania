package dungeonmania.entities.collectableEntity.breakableEntity;

public class Anduril extends BreakableEntity {
    // Temporary
    // durability attribute of anduril is set to 10 (ticks)
    private int durability = 8;
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Anduril(int x, int y, String type) {
        super(x, y, "anduril");
        setId("Anduril" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Anduril.counter = counter;
    }
}
