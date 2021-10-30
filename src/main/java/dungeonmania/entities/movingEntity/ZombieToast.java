package dungeonmania.entities.movingEntity;

import dungeonmania.Dungeon;
import dungeonmania.entities.collectableEntity.breakableEntity.Armour;

public class ZombieToast extends MovingEntity {
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
    Boolean hasArmour;
    Dungeon dungeon;

    public ZombieToast(int x, int y, Dungeon dungeon) {
        super(x, y, "zombie_toast", 5, 5);
        setId("ZombieToast" + String.valueOf(counter));
        counter++;
        this.hasArmour = Math.random() <= 0.2;
        this.dungeon = dungeon;
    }

    public void move() {
        
    }

    public void dropArmour() {
        if (!hasArmour) {
            return;
        }
        Armour armour = new Armour(this.getX(), this.getY());
        dungeon.addItem(armour);
    }

    public void randomise() {

    }
}
