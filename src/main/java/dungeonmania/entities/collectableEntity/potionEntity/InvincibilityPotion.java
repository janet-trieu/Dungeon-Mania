package dungeonmania.entities.collectableEntity.potionEntity;

public class InvincibilityPotion extends PotionEntity {

    // will be changed later
    private int duration = 0;

    // storing the number of Invincibility potions created to help with fluid entityId generation
    private static int counter = 0;

    public InvincibilityPotion(int x, int y) {
        super(x, y, "invincibility_potion");
        setId("InvincibilityPotion" + String.valueOf(counter));
        counter++;
        setDuration(duration);
    }

}
