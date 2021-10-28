package dungeonmania.entities.collectableEntity.breakableEntity.buildableEntity;

public class Bow extends BuildableEntity {
    private int durability = 3;

    public Bow(int x, int y) {
        super(x, y, "bow");
        setDurability(durability);
    }
    
}
