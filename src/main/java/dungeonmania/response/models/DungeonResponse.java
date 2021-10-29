package dungeonmania.response.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DungeonResponse {
    private final String dungeonId;
    private final String dungeonName;
    private final List<EntityResponse> entities;
    private final List<ItemResponse> inventory;
    private final List<String> buildables;
    private final String goals;
    private final List<AnimationQueue> animations;

    public DungeonResponse(String dungeonId, String dungeonName, List<EntityResponse> entities,
            List<ItemResponse> inventory, List<String> buildables, String goals) {
        this(dungeonId, dungeonName, entities, inventory, buildables, goals, new ArrayList<>());
    }

    public DungeonResponse(String dungeonId, String dungeonName, List<EntityResponse> entities,
            List<ItemResponse> inventory, List<String> buildables, String goals,
            List<AnimationQueue> animations) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.entities = entities;
        this.inventory = inventory;
        this.buildables = buildables;
        this.goals = goals;
        this.animations = animations;
    }

    public List<AnimationQueue> getAnimations() {
        return animations;
    }

    public final String getDungeonName() {
        return dungeonName;
    }

    public final List<ItemResponse> getInventory() {
        return inventory;
    }

    public final List<String> getBuildables() {
        return buildables;
    }

    public final String getGoals() {
        return goals;
    }

    public final String getDungeonId() {
        return dungeonId;
    }

    public final List<EntityResponse> getEntities() {
        return entities;
    }

    @Override
    public String toString() {
        // NO ANIMATION QUEUE FOR NOW
        return "DungeonResponse [dungeonId=" + dungeonId + ", dungeonName=" + dungeonName + ", entities=" + entities + ", inventory=" + inventory +
                ", buildables=" + buildables + ", goals=" + goals + "]";
    }

    @Override
    public boolean equals(Object obj) {
        // NO ANIMATION QUEUE FOR NOW
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        DungeonResponse other = (DungeonResponse) obj;
        return Objects.equals(dungeonId, other.dungeonId) && Objects.equals(dungeonName, other.dungeonName)
                && Objects.equals(entities, other.entities) && Objects.equals(inventory, other.inventory)
                && Objects.equals(buildables, other.buildables) && Objects.equals(goals, other.goals);
    }
}
