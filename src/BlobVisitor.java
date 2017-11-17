public class BlobVisitor extends AllFalseEntityVisitor {

    @Override
    public Boolean visit(Blob blob) {
        return true;
    }
}
