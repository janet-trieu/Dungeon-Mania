package dungeonmania.response.models;

import java.util.Objects;

public final class ItemResponse {
    private final String id;
    private final String type;

    public ItemResponse(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public final String getType() {
        return type;
    }

    public final String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ItemResponse [id=" + id + ", type=" + type + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        ItemResponse other = (ItemResponse) obj;
        return Objects.equals(id, other.id) && Objects.equals(type, other.type);
    }
}
