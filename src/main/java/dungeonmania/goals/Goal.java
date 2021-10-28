package dungeonmania.goals;

public abstract class Goal {
    private Boolean complete;

    public Goal() {
        // TODO
    }

    @Override
    public String toString() {
        // TODO
        return null;
    }

    public Boolean isComplete() {
        //TODO
        return null;
    }

    public Boolean isLeaf() {
        // TODO
        return null;
    }

    public void update() {
        // TODO
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
    
}
