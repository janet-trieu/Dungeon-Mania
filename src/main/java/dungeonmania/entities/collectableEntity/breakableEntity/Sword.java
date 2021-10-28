package dungeonmania.entities.collectableEntity.breakableEntity;

public class Sword extends BreakableEntity {
    private int durability = 6;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Sword(int x, int y) {
        super(x, y, "sword");
        setId("sword" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }
    
}
