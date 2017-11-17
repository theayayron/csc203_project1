public interface EntityVisitor<R> {

    R visit(Blob blob);
    R visit(Miner miner);
    R visit(Obstacle obstacle);
    R visit(Ore ore);
    R visit(Quake quake);
    R visit(Smith smith);
    R visit(Vein vein);

}
