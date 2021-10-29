package dungeonmania.entities.movingEntity;

public class Spider extends MovingEntity {
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
    private int movementCounter;

    public Spider(int x, int y) {
        super(x, y, "spider", 1, 10);
        setId("Spider" + String.valueOf(counter));
        counter++;
    }

    public void move() {
        
    }
}
