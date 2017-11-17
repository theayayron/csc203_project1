public class QuakeVisitor extends AllFalseEntityVisitor{

    @Override
    public Boolean visit(Quake quake) {
        return true;
    }
}
