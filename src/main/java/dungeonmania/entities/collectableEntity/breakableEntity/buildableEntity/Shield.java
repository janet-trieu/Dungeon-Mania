package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

public class Shield extends BuildableEntity {
    private int durability = 5;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Shield(int x, int y) {
        super(x, y, "shield");
        setId("Shield" + String.valueOf(counter));
        counter++;
        setDurability(durability);
    }

}
