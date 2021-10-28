package dungeonmania.entities.collectableEntity.potionEntity;

public class InvincibilityPotion extends PotionEntity {

    // will be changed later
    private int duration = 0;

    public InvincibilityPotion(int x, int y) {
        super(x, y, "invincibility_potion");
        setDuration(duration);
    }

}
