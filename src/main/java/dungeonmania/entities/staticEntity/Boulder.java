package dungeonmania.entities.staticEntity;

public class Boulder extends StaticEntity {
    
    private int layer = 1;

    public Boulder(int x, int y, String type) {
        super(x, y, type);
        setLayer(layer);
    }

}
