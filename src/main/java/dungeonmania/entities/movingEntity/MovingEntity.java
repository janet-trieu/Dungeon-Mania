package dungeonmania.entities.movingEntity;

import dungeonmania.entities.Entity;

public abstract class MovingEntity extends Entity {

    private int health;
    private int damage;
   
    public MovingEntity(int x, int y, String type, int health, int damage) {
        super(x,y,type);
        this.health = health;
        this.damage = damage;
    }

    public int getHealth() {return health;}

    public void setHealth(int health) {this.health = health;}

    public int getDamage() {return damage;}

    public void setDamage(int damage) {this.damage = damage;} 

    
}
