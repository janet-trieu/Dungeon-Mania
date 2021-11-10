package dungeonmania.entities.movingEntity;

import dungeonmania.Dungeon;

public class Hydra extends ZombieToast{
    
    int counter = 0;

    public Hydra(int x, int y, Dungeon dungeon){
        super(x, y, dungeon, "hydra");
        setId("Hydra" + String.valueOf(counter));
        counter++;
    }
}
