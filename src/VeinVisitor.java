public class VeinVisitor extends AllFalseEntityVisitor {
    @Override
    public Boolean visit(Vein vein) {
        return true;
    }
}
