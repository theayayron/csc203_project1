public class OreVisitor extends AllFalseEntityVisitor {

    @Override
    public Boolean visit(Ore ore) {
        return true;
    }
}
