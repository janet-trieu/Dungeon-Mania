package dungeonmania.entities.collectableEntity.breakableEntity;

public class Armour extends BreakableEntity {
    private int durability = 8;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Armour(int x, int y) {
        super(x, y, "armour");
        setId("armour" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }

}
