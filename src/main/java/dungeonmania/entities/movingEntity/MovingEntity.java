package dungeonmania.entities.movingEntity;

import dungeonmania.entities.Entity;

public abstract class MovingEntity extends Entity {
    // Moving entities will always be in layer 3
    private int movingLayer = 3;
    private double health;
    private double damage;
   
    public MovingEntity(int x, int y, String type, int health, int damage) {
        super(x,y,type);
        this.health = health;
        this.damage = damage;
        setLayer(movingLayer);
    }

    public double getHealth() {return health;}

    public void setHealth(double health) {this.health = health;}

    public double getDamage() {return damage;}

    public void setDamage(double damage) {this.damage = damage;}

    public abstract void move();
}
