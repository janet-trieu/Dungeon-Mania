package dungeonmania.entities.staticEntity;

public class Boulder extends StaticEntity {
    
    private int layer = 1;

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Boulder(int x, int y) {
        super(x, y, "boulder");
        setId("Boulder" + String.valueOf(counter));
        counter++;
        setLayer(layer);
    }

}
