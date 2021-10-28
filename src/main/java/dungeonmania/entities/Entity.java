package dungeonmania.entities;

import dungeonmania.util.Position;

public abstract class Entity {
    private int x;
    private int y;
    private int layer;
    private String id;
    private String type;
    private Boolean isInteractable;
    
    /**
     * Constructor for Entity with x, y, and type
     * @param x
     * @param y
     * @param type
     */
    public Entity(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.isInteractable = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsInteractable() {
        return isInteractable;
    }

    public void setIsInteractable(Boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public Position getPosition() {
        return new Position(x, y, layer);
    }
}
