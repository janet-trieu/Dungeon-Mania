package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

public class Bow extends BuildableEntity {
    private int durability = 3;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Bow(int x, int y) {
        super(x, y, "bow");
        setId("Bow" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }
    
}
