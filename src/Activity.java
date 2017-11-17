public class Activity implements Action{

    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;

    public Activity(Entity entity, WorldModel world,
                  ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler)
    {
        executeActivityAction(scheduler);
    }

    private  void executeActivityAction(EventScheduler scheduler) {
        Class<?> type = entity.getClass();

        if (entity instanceof Miner) {
            Miner miner = (Miner) entity;
            miner.executeActivity(world, imageStore, scheduler);


        } else if (entity instanceof Ore) {
            Ore ore = (Ore) entity;
            ore.executeActivity(world, imageStore,
                    scheduler);

        } else if (entity instanceof Blob) {
            Blob blob = (Blob) entity;
            blob.executeActivity(world,
                    imageStore, scheduler);

        } else if (entity instanceof Quake) {
            Quake quake = (Quake) entity;
            quake.executeActivity(world, imageStore,
                    scheduler);

        } else if (entity instanceof Vein) {
            Vein vein = (Vein) entity;
            vein.executeActivity(world, imageStore,
                    scheduler);

        } else {
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            entity.getClass()));
        }
    }
}
