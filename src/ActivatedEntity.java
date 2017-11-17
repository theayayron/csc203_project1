public interface ActivatedEntity extends Entity {



    Action createActivityAction(WorldModel world, ImageStore imageStore);

    int getActionPeriod();

    void scheduleActivityActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
//    {
//        switch (kind)
//        {
//            case MINER_FULL:
//                scheduler.scheduleEvent(this,
//                        createActivityAction(world, imageStore),
//                        getActionPeriod());
//                scheduler.scheduleEvent(this, createAnimationAction(0),
//                        getAnimationPeriod());
//                break;
//
//            case MINER_NOT_FULL:
//                scheduler.scheduleEvent(this,
//                        this.createActivityAction(world, imageStore),
//                        this.getActionPeriod());
//                scheduler.scheduleEvent(this,
//                        this.createAnimationAction(0), this.getAnimationPeriod());
//                break;
//
//            case ORE:
//                scheduler.scheduleEvent(this,
//                        this.createActivityAction(world, imageStore),
//                        this.getActionPeriod());
//                break;
//
//            case ORE_BLOB:
//                scheduler.scheduleEvent(this,
//                        this.createActivityAction(world, imageStore),
//                        this.getActionPeriod());
//                scheduler.scheduleEvent(this,
//                        this.createAnimationAction(0), this.getAnimationPeriod());
//                break;
//
//            case QUAKE:
//                scheduler.scheduleEvent(this,
//                        this.createActivityAction(world, imageStore),
//                        this.getActionPeriod());
//                scheduler.scheduleEvent(this,
//                        this.createAnimationAction(Entity.QUAKE_ANIMATION_REPEAT_COUNT),
//                        this.getAnimationPeriod());
//                break;
//
//            case VEIN:
//                scheduler.scheduleEvent(this,
//                        this.createActivityAction(world, imageStore),
//                        this.getActionPeriod());
//                break;
//
//            default:
//        }
//    }
}
