package dungeonmania.entities.movingEntity;

public class Mercenary extends MovingEntity {
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
    private boolean isInteractable = true;
    Boolean hasArmour;
    Boolean isBribed = false;

    public Mercenary(int x, int y) {
        super(x, y, "mercenary", 10, 1);
        setId("Mercenary" + String.valueOf(counter));
        setIsInteractable(isInteractable);
        counter++;
    }

    public void move() {

    }

    public void dropArmour() {
        
    }


}
