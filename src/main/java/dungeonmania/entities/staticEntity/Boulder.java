package dungeonmania.entities.staticEntity;

public class Boulder extends StaticEntity {
    
    private int layer = 1;

    public Boulder(int x, int y) {
        super(x, y, "boulder");
        setLayer(layer);
    }

}
