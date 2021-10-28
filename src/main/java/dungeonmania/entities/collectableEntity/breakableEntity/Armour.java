package dungeonmania.entities.collectableEntity.breakableEntity;

public class Armour extends BreakableEntity {
    private int durability = 8;

    public Armour(int x, int y) {
        super(x, y, "armour");
        setDurability(durability);
    }

}
