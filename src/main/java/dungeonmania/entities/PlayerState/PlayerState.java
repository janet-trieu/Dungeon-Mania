package dungeonmania.entities.PlayerState;

public interface PlayerState {
    /**
     * Interface for all PlayerStates
     */
    public void applyEffect();
    public void loadDuration(int duration);
    public void removeEffect();
    public void reduceDuration();
    public Boolean isApplied();
}
