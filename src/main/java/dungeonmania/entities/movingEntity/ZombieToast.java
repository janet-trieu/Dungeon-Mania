package dungeonmania.entities.movingEntity;

public class ZombieToast extends MovingEntity {
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
    Boolean hasArmour;

    public ZombieToast(int x, int y) {
        super(x, y, "zombie_toast", 5, 5);
        setId("ZombieToast" + String.valueOf(counter));
        counter++;
    }

    public void move() {
        
    }

    public void dropArmour() {

    }

    public void randomise() {

    }
}
