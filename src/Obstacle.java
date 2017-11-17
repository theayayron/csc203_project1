import processing.core.PImage;

import java.util.List;

public class Obstacle extends AbstractEntity{

    public static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;


    public Obstacle(String id, Point position, List<PImage> images) {
        super(id, position, images, 0);
    }

    // leave here with process line  -- private
    static boolean parseObstacle(String[] properties, WorldModel world,
                                 ImageStore imageStore)
    {
       if (properties.length == OBSTACLE_NUM_PROPERTIES)
       {
          Point pt = new Point(
                  Integer.parseInt(properties[OBSTACLE_COL]),
                  Integer.parseInt(properties[OBSTACLE_ROW]));
          Obstacle obstacle = createObstacle(properties[OBSTACLE_ID],
                  pt, imageStore.getImageList(OBSTACLE_KEY));
          world.tryAddEntity(obstacle);
       }

       return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    public static Obstacle createObstacle(String id, Point position,
                                        List<PImage> images)
    {
       return new Obstacle(id, position, images);
    }

    @Override
    public <R> R accept(EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
