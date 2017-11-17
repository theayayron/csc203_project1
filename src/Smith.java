import processing.core.PImage;
import java.util.List;

public class Smith extends AbstractEntity{
    public static final String SMITH_KEY = "blacksmith";
    private static final int SMITH_NUM_PROPERTIES = 4;
    private static final int SMITH_ID = 1;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ROW = 3;

    public Smith(String id, Point position, List<PImage> images) {
        super(id, position, images, 0);
    }

    // leave here with processline -- private
    public static boolean parseSmith(String[] properties, WorldModel world,
                              ImageStore imageStore)
    {
       if (properties.length == SMITH_NUM_PROPERTIES)
       {
          Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                  Integer.parseInt(properties[SMITH_ROW]));
          Smith smith = createBlacksmith(properties[SMITH_ID],
                  pt, imageStore.getImageList(SMITH_KEY));
          world.tryAddEntity(smith);
       }

       return properties.length == SMITH_NUM_PROPERTIES;
    }

    public static Smith createBlacksmith(String id, Point position,
                                          List<PImage> images)
    {
       return new Smith(id, position, images);
    }

    @Override
    public <R> R accept(EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
