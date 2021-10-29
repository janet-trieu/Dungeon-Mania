package dungeonmania.entities.movingEntity;

public class Mercenary extends MovingEntity {
    Boolean hasArmour;
    Boolean isBribed = false;

    public Mercenary(int x, int y) {
        super(x, y, "mercenary", 10, 1);
    }

    public void move() {

    }

    public void dropArmour() {
        
    }


}
