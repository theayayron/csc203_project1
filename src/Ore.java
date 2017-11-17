import processing.core.PImage;

import java.util.List;

public class Ore extends AbstractActiveEntity{


    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;

    public static final String ORE_KEY = "ore";
    private static final int ORE_NUM_PROPERTIES = 5;
    private static final int ORE_ID = 1;
    private static final int ORE_COL = 2;
    private static final int ORE_ROW = 3;
    private static final int ORE_ACTION_PERIOD = 4;

    public Ore(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    // leave here with process line -- private
    static boolean parseOre(String[] properties, WorldModel world,
                            ImageStore imageStore)
    {
       if (properties.length == ORE_NUM_PROPERTIES)
       {
          Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                  Integer.parseInt(properties[ORE_ROW]));
          Ore ore = createOre(properties[ORE_ID],
                  pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                  imageStore.getImageList(ORE_KEY));
          world.tryAddEntity(ore);
       }

       return properties.length == ORE_NUM_PROPERTIES;
    }

    public static Ore createOre(String id, Point position, int actionPeriod,
                                   List<PImage> images)
    {
       return new Ore(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = super.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Blob blob = Blob.createOreBlob(super.getID() + BLOB_ID_SUFFIX,
                pos, super.getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN +
                        Functions.rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }

}
