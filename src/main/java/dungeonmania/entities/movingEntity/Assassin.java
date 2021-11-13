package dungeonmania.entities.movingEntity;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;

public class Assassin extends Mercenary {
    
    private static int counter = 0;

    public Assassin(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon, "assassin"); 
        setId("Assassin" + String.valueOf(counter));
        counter++;
    }

    @Override
    public void takeDamagePlayer(Player player) {
        if (player.isAnduril()) {
            super.takeDamage(player.getHealth(), player.getDamage() * 3);
        } else {
            super.takeDamage(player.getHealth(), player.getDamage());
        }
    }
}
