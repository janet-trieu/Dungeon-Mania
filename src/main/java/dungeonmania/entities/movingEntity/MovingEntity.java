package dungeonmania.entities.movingEntity;

import dungeonmania.entities.Entity;

public abstract class MovingEntity extends Entity {

    // Moving entities will always be in layer 3
    private int layer = 3;

    // storing the attribute of moving entity's health
    private double health;

    // storing the attribute of moving entity's damage
    private double damage;
   
    /**
     * Constructor for a moving entity
     * @param x position
     * @param y position
     * @param type of moving entity
     * @param health of moving entity
     * @param damage of moving entity
     */
    public MovingEntity(int x, int y, String type, int health, int damage) {
        super(x,y,type);
        this.health = health;
        this.damage = damage;
        setLayer(layer);
    }

    /**
     * Abstract method to simulate the movement of moving entities
     */
    public abstract void move();

    /**
     * Getter for health of moving entity
     * @return
     */
    public double getHealth() {return health;}

    /**
     * Setter for health of moving entity
     * @param health
     */
    public void setHealth(double health) {this.health = health;}

    /**
     * Getter for the damage of moving entity
     * @return
     */
    public double getDamage() {return damage;}

    /**
     * Setter for the damage of moving entity
     * @param damage
     */
    public void setDamage(double damage) {this.damage = damage;}

}
