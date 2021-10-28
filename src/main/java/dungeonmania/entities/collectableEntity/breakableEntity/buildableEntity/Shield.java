package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

public class Shield extends BuildableEntity {
    private int durability = 5;

    public Shield(int x, int y) {
        super(x, y, "shield");
        setDurability(durability);
    }

}
