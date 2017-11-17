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

        if (entity.accept(new MinerVisitor())) {
            Miner miner = (Miner) entity;
            miner.executeActivity(world, imageStore, scheduler);


        } else if (entity.accept(new OreVisitor())) {
            Ore ore = (Ore) entity;
            ore.executeActivity(world, imageStore,
                    scheduler);

        } else if (entity.accept(new BlobVisitor())) {
            Blob blob = (Blob) entity;
            blob.executeActivity(world,
                    imageStore, scheduler);

        } else if (entity.accept(new QuakeVisitor())) {
            Quake quake = (Quake) entity;
            quake.executeActivity(world, imageStore,
                    scheduler);

        } else if (entity.accept(new VeinVisitor())) {
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
