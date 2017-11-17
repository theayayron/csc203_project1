import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Vein extends AbstractActiveEntity{

    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    private static final int ORE_REACH = 1;

    public static final String VEIN_KEY = "vein";
    private static final int VEIN_NUM_PROPERTIES = 5;
    private static final int VEIN_ID = 1;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_ACTION_PERIOD = 4;

    public Vein(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    // leave here with process line -- private
    static boolean parseVein(String[] properties, WorldModel world,
                             ImageStore imageStore)
    {
       if (properties.length == VEIN_NUM_PROPERTIES)
       {
          Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                  Integer.parseInt(properties[VEIN_ROW]));
          Vein vein = createVein(properties[VEIN_ID],
                  pt,
                  Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                  imageStore.getImageList(VEIN_KEY));
          world.tryAddEntity(vein);
       }

       return properties.length == VEIN_NUM_PROPERTIES;
    }

    public static Vein createVein(String id, Point position, int actionPeriod,
                                    List<PImage> images)
    {
       return new Vein(id, position, images, actionPeriod);
    }

    private static Optional<Point> findOpenAround(WorldModel world, Point pos)
    {
       for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
       {
          for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
          {
             Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
             if (world.withinBounds(newPt) &&
                     !world.isOccupied(newPt))
             {
                return Optional.of(newPt);
             }
          }
       }

       return Optional.empty();
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = findOpenAround(world, super.getPosition());

        if (openPt.isPresent())
        {
            Ore ore = Ore.createOre(ORE_ID_PREFIX + super.getID(),
                    openPt.get(), ORE_CORRUPT_MIN +
                            Functions.rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    imageStore.getImageList(Ore.ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActivityActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                super.getActionPeriod());
    }

    @Override
    public <R> R accept(EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
