import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Thief extends MovingEntity {

    private static final String THIEF_KEY = "thief";

    public Thief(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int repeatCount) {
        super(id, position, images, actionPeriod, animationPeriod, repeatCount);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Miner> target = world.findNearestMiner(super.getPosition());

        if (!target.isPresent())
        {
            Point tgtPos = target.get().getPosition();

            if ()
            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    super.getActionPeriod());
        }
    }

    private boolean moveToThief(WorldModel world, Miner target, EventScheduler scheduler)
    {
        if (super.getPosition().adjacent((target.getPosition())))
        {
            target.transformFull(world, scheduler, target.getI)
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

    @Override
    public <R> R accept(EntityVisitor<R> visitor) {
        return null;
    }
}
