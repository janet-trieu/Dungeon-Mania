package dungeonmania.entities.movingEntity;

import dungeonmania.Dungeon;

public class Assassin extends Mercenary {
    
    private static int counter = 0;

    public Assassin(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon, "assassin"); 
        setId("Assassin" + String.valueOf(counter));
        counter++;
    }
    
}
