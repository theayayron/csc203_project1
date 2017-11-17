import processing.core.PImage;

import java.util.List;

public abstract class AbstractAnimatedEntity
        extends AbstractActiveEntity implements AnimatedEntity{

    private int animationPeriod;
    private int repeatCount;

    public AbstractAnimatedEntity(String id, Point position,
                                  List<PImage> images, int actionPeriod,
                                  int animationPeriod, int repeatCount)
    {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
        this.repeatCount = repeatCount;
    }

    public Animation createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }

    public void scheduleAnimationActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, createAnimationAction(repeatCount),
                animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world,
                                ImageStore imageStore)
    {
        this.scheduleActivityActions(scheduler, world, imageStore);
        this.scheduleAnimationActions(scheduler, world, imageStore);
    }

    public int getAnimationPeriod() {return animationPeriod;}


    @Override
    public void nextImage() {
        imageIndex = (imageIndex + 1) % getImages().size();
    }

}
