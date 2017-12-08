import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Miner extends MovingEntity{

    public static final String MINER_KEY = "miner";
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int MINER_ID = 1;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ACTION_PERIOD = 5;
    private static final int MINER_ANIMATION_PERIOD = 6;

    private int resourceCount;
    private int resourceLimit;

    public Miner(String id, Point position, List<PImage> images,
                 int actionPeriod, int animationPeriod, int repeatCount,
                 int resourceCount, int resourceLimit)
    {
        super(id, position, images, actionPeriod, animationPeriod, repeatCount);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }


    // leave here with process line -- private
    public static boolean parseMiner(String[] properties, WorldModel world,
                              ImageStore imageStore)
    {
       if (properties.length == MINER_NUM_PROPERTIES)
       {
          Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                  Integer.parseInt(properties[MINER_ROW]));
          Miner miner = createMinerNotFull(properties[MINER_ID],
                  Integer.parseInt(properties[MINER_LIMIT]),
                  pt,
                  Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                  Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                  imageStore.getImageList(MINER_KEY));
          world.tryAddEntity(miner);
       }

       return properties.length == MINER_NUM_PROPERTIES;
    }

    private static Miner createMinerFull(String id, int resourceLimit,
                                         Point position, int actionPeriod, int animationPeriod,
                                         List<PImage> images)
    {
       return new Miner(id, position, images, actionPeriod, animationPeriod,
               0, resourceLimit, resourceLimit);
    }

    private static Miner createMinerNotFull(String id, int resourceLimit,
                                            Point position, int actionPeriod, int animationPeriod,
                                            List<PImage> images)
    {
       return new Miner(id, position, images,actionPeriod, animationPeriod,
               0, 0, resourceLimit);
    }

    private boolean isFull() {
        return resourceCount >= resourceLimit;
    }

    public boolean isEmtpy() { return resourceCount < 1;}

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        if (isFull()) {
            Optional<Entity> fullTarget = world.findNearest(super.getPosition(),
                    Smith.class);

            if (fullTarget.isPresent() &&
                    this.moveToFull(world, fullTarget.get(), scheduler)) {
                this.transformFull(world, scheduler, imageStore);
            } else {
                scheduler.scheduleEvent(this,
                        this.createActivityAction(world, imageStore),
                        super.getActionPeriod());
            }
        } else {
            Optional<Entity> notFullTarget = world.findNearest(super.getPosition(),
                    Ore.class);

            if (!notFullTarget.isPresent() ||
                    !this.moveToNotFull(world, notFullTarget.get(), scheduler) ||
                    !this.transformNotFull(world, scheduler, imageStore))
            {
                scheduler.scheduleEvent(this,
                        this.createActivityAction(world, imageStore),
                        super.getActionPeriod());
            }
        }
    }

    private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (resourceCount >= resourceLimit)
        {
            Miner miner = createMinerFull(super.getID(), resourceLimit,
                    super.getPosition(), super.getActionPeriod(),
                    super.getAnimationPeriod(), super.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Miner miner = createMinerNotFull(super.getID(), resourceLimit,
                super.getPosition(), super.getActionPeriod(),
                super.getAnimationPeriod(), super.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    private boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (super.getPosition().adjacent(target.getPosition()))
        {
            resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!super.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    private boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {

        if (super.getPosition().adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!super.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void makeGreedy(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        resourceLimit = resourceLimit * 3;
        Miner miner = createMinerNotFull(super.getID(), resourceLimit,
                super.getPosition(), super.getActionPeriod(),
                super.getAnimationPeriod(), imageStore.getImageList("greedy_miner"));

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);

    }

    @Override
    protected Boolean canPassThrough(Point p, WorldModel world) {
        return !world.isOccupied(p) && world.withinBounds(p);
    }

    @Override
    public <R> R accept(EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
