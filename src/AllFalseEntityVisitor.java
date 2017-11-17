public class AllFalseEntityVisitor  implements EntityVisitor<Boolean> {

    public Boolean visit(Blob blob) { return false; }
    public Boolean visit(Miner miner){ return false; }
    public Boolean visit(Obstacle obstacle){ return false; }
    public Boolean visit(Ore ore){ return false; }
    public Boolean visit(Quake quake){ return false; }
    public Boolean visit(Smith smith){ return false; }
    public Boolean visit(Vein vein){ return false; }
}
