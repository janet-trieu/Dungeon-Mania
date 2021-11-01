package dungeonmania.entities.collectableEntity.potionEntity;

public class InvincibilityPotion extends PotionEntity {

    // storing the duration of invincibility potion as 10 (ticks)
    private int duration = 10;

    // storing the number of Invincibility potions created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for an invincibility potion
     * @param x position
     * @param y position
     */
    public InvincibilityPotion(int x, int y) {
        super(x, y, "invincibility_potion");
        setId("InvincibilityPotion" + String.valueOf(counter));
        counter++;
        setDuration(duration);
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        InvincibilityPotion.counter = counter;
    }
    
}
