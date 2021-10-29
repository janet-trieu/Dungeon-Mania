package dungeonmania.entities.collectableEntity.potionEntity;

public class InvisibilityPotion extends PotionEntity {
    
    // will be changed later
    private int duration = 0;

    // storing the number of Invisibility potions created to help with fluid entityId generation
    private static int counter = 0;

    public InvisibilityPotion(int x, int y) {
        super(x, y, "invisibility_potion");
        setId("InvisibilityPotion" + String.valueOf(counter));
        counter++;
        setDuration(duration);
    }

}
