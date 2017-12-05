import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Thief extends MovingEntity {

    public static final String THIEF_KEY = "thief";
    private static final int THIEF_NUM_PROPERTIES = 7;
    private static final int THIEF_ID = 1;
    private static final int THIEF_COL = 2;
    private static final int THIEF_ROW = 3;
    private static final int THIEF_LIMIT = 4;
    private static final int THIEF_ACTION_PERIOD = 5;
    private static final int THIEF_ANIMATION_PERIOD = 6;

    public Thief(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int repeatCount) {
        super(id, position, images, actionPeriod, animationPeriod, repeatCount);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Miner> target = world.findNearestMiner(super.getPosition());

        if (target.isPresent())
        {
            Point tgtPos = target.get().getPosition();

            if (moveToThief(world, target.get(), scheduler, imageStore)) {
                target.get().transformFull(world, scheduler, imageStore);
            }

        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                super.getActionPeriod());
    }

    private boolean moveToThief(WorldModel world, Miner target, EventScheduler scheduler, ImageStore imageStore)
    {
        if (super.getPosition().adjacent((target.getPosition())))
        {
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!super.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if(occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    @Override
    protected Boolean canPassThrough(Point p, WorldModel world) {
        return !world.isOccupied(p) && world.withinBounds(p);
    }

    public static Thief createThief(Miner miner, ImageStore imageStore) {
        return new
                Thief(miner.getID(),
                miner.getPosition(),
                imageStore.getImageList(THIEF_KEY),
                miner.getActionPeriod()/2,
                miner.getAnimationPeriod()/2,
                0);
    }

    private static Thief createThief(String id, Point position, int actionPeriod, int animationPeriod,
                                            List<PImage> images)
    {
        return new Thief(id, position, images, actionPeriod, animationPeriod, 0);
    }

    public static boolean parseThief(String[] properties, WorldModel world,
                                     ImageStore imageStore) {
        if (properties.length == THIEF_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[THIEF_COL]),
                    Integer.parseInt(properties[THIEF_ROW]));
            Thief thief = createThief(properties[THIEF_ID],
                    pt,
                    Integer.parseInt(properties[THIEF_ACTION_PERIOD]),
                    Integer.parseInt(properties[THIEF_ANIMATION_PERIOD]),
                    imageStore.getImageList(THIEF_KEY));
            world.tryAddEntity(thief);
        }

        return properties.length == THIEF_NUM_PROPERTIES;
    }

    @Override
    public <R> R accept(EntityVisitor<R> visitor) {
        return null;
    }
}
