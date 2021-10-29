package dungeonmania.response.models;

import java.util.Objects;

import dungeonmania.util.Position;

public final class EntityResponse {
    private final String id;
    private final String type;
    private final Position position;
    private final boolean isInteractable;

    public EntityResponse(String id, String type, Position position, boolean isInteractable) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.isInteractable = isInteractable;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public final String getId() {
        return id;
    }

    public final String getType() {
        return type;
    }

    public final Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "EntityResponse [id=" + id + ", type=" + type + ", position=" + position + ", isInteractable=" + isInteractable +"]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        EntityResponse other = (EntityResponse) obj;
        return Objects.equals(id, other.id) && Objects.equals(type, other.type)
                && Objects.equals(position, other.position) && Objects.equals(isInteractable, other.isInteractable);
    }
}
