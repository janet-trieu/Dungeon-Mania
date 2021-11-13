package dungeonmania.entities.movingEntity;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class Hydra extends ZombieToast{
    
    int counter = 0;


    public Hydra(int x, int y, Dungeon dungeon){
        super(x, y, dungeon, "hydra");
        setId("Hydra" + String.valueOf(counter));
        counter++;
    }

    @Override
    public void takeDamage(double otherHealth, double otherDamage) {
        if (getRandom().nextInt(100) % 2 == 0) {
            super.takeDamage(otherHealth, otherDamage);
        } else {
            setHealth(getHealth() + 5);
        }
    }

    @Override
    public void takeDamagePlayer(Player player) {
        if (player.isAnduril()) {
            super.takeDamage(player.getHealth(), player.getDamage() * 3);
        } else {
            takeDamage(player.getHealth(), player.getDamage());
        }
    }

}
