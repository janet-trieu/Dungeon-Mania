package dungeonmania.entities.movingEntity;

import dungeonmania.Dungeon;
import dungeonmania.entities.collectableEntity.breakableEntity.Armour;

public class Mercenary extends MovingEntity {
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;
    private boolean isInteractable = true;
    Boolean hasArmour;
    Boolean isBribed = false;
    Dungeon dungeon;

    public Mercenary(int x, int y, Dungeon dungeon) {
        super(x, y, "mercenary", 10, 1);
        setId("Mercenary" + String.valueOf(counter));
        setIsInteractable(isInteractable);
        counter++;
        this.hasArmour = Math.random() <= 0.2;
        this.dungeon = dungeon;
        
    }

    public void move() {

    }

    public void ShortestPath() {

    }



    public void dropArmour() {
        if (!hasArmour) {
            return;
        }
        Armour armour = new Armour(this.getX(), this.getY());
        dungeon.addItem(armour);
    }


}
