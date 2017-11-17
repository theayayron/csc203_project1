import processing.core.PImage;

import java.util.List;

public abstract class AbstractActiveEntity
        extends AbstractEntity implements ActivatedEntity
{

    private int actionPeriod;

    public AbstractActiveEntity(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, 0);
        this.actionPeriod = actionPeriod;
    }

    @Override
    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    @Override
    public int getActionPeriod() {
        return actionPeriod;
    }

    @Override
    public void scheduleActivityActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                getActionPeriod());
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world,
                                ImageStore imageStore)
    {
        this.scheduleActivityActions(scheduler, world, imageStore);
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore,
                                   EventScheduler scheduler);
}
