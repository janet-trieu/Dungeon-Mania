package dungeonmania.entities.staticEntity;

public class Wall extends StaticEntity {

    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public Wall(int x, int y) {
        super(x, y, "wall");
        setId("wall" + String.valueOf(counter));
        counter++;
    }
    
}
