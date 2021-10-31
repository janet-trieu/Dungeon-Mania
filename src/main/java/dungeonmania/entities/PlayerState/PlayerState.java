package dungeonmania.entities.PlayerState;

public interface PlayerState {
    /**
     * Interface for all PlayerStates
     */
    public void applyEffect();
    public void removeEffect();
    public void reduceDuration();
    public int getDuration();
    public Boolean isApplied();
}
