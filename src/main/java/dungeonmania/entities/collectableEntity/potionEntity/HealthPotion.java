package dungeonmania.entities.collectableEntity.potionEntity;

public class HealthPotion extends PotionEntity {
   
    // storing the number of Health potions created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for a health potion
     * @param x position
     * @param y position
     */
    public HealthPotion(int x, int y) {
        super(x, y, "health_potion");
        setId("HealthPotion" + String.valueOf(counter));
        counter++;
    }
    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        HealthPotion.counter = counter;
    }
}
