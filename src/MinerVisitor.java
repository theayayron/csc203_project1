public class MinerVisitor extends AllFalseEntityVisitor{

    @Override
    public Boolean visit(Miner miner) {
        return true;
    }
}
