package dungeonmania.entities.movingEntity;

public interface Bribeable {
    
    public void bribe();

    public boolean isBribed();

    public void setIsBribed(boolean isBribed);

    /**
     * Method to check whether mind control has expired or not
     */
    public void updateMindControl();

    /**
     * Method to check whether bribeable entity is mind controlled
     * @return true if currently mind controlled
     * @return false if currently not mind controlled
     */
    public boolean isMindControlled();

    public int getMindControlDuration();

    public void setMindControlDuration(int mindControlDuration);

}
