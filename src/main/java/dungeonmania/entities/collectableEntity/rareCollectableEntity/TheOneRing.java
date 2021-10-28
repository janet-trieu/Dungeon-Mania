package dungeonmania.entities.collectableEntity.rareCollectableEntity;

public class TheOneRing extends RareCollectableEntity {
    
    // storing the number of entities created to help with fluid entityId generation
    private static int counter = 0;

    public TheOneRing(int x, int y) {
        super(x, y, "the_one_ring");
        setId("the_one_ring" + String.valueOf(counter));
        counter++;
    }

}
