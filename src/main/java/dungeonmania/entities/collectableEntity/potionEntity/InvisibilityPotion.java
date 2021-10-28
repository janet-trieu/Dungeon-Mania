package dungeonmania.entities.collectableEntity.potionEntity;

public class InvisibilityPotion extends PotionEntity {
    
    // will be changed later
    private int duration = 0;

    public InvisibilityPotion(int x, int y) {
        super(x, y, "invisibility_potion");
        setDuration(duration);
    }

}
