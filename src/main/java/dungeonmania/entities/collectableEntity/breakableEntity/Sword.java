package dungeonmania.entities.collectableEntity.breakableEntity;

public class Sword extends BreakableEntity {
    private int durability = 6;

    public Sword(int x, int y) {
        super(x, y, "sword");
        setDurability(durability);
    }
    
}
