public interface AnimatedEntity extends Entity {

    Action createAnimationAction(int repeatCount);

    int getAnimationPeriod();

    void scheduleAnimationActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

    void nextImage();
}
