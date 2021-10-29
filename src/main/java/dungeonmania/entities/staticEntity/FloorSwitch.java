package dungeonmania.entities.staticEntity;

public class FloorSwitch extends StaticEntity {

    // storing the boolean of whether floor switch is activated by a boulder
    private Boolean isActive = false;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    /**
     * Floor switch constructor
     * @param x position
     * @param y position
     */
    public FloorSwitch(int x, int y) {
        super(x, y, "switch");
        setId("switch" + String.valueOf(counter));
        counter++;
    }

    /**
     * Getter for isActive of floor switch
     * @return isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * Setter for isActive of floor switch
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
}
