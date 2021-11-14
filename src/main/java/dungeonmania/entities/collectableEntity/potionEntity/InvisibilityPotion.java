package dungeonmania.entities.collectableEntity.potionEntity;

public class InvisibilityPotion extends PotionEntity {
    
    // storing the duration of invisibility potion as 10 (ticks)
    private int duration = 10;

    // storing the number of Invisibility potions created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Constructor for an invisibility potion
     * @param x position 
     * @param y position
     */
    public InvisibilityPotion(int x, int y) {
        super(x, y, "invisibility_potion");
        setId("InvisibilityPotion" + String.valueOf(counter));
        counter++;
        setDuration(duration);
    }

    public static void setCounter(int counter) {
        InvisibilityPotion.counter = counter;
    }
    
}
