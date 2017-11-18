public class WorldNode {

    private Point cameFrom;
    private Point position;
    private int gScore;
    private int hScore;
    private int fScore;

    public WorldNode(Point cameFrom, Point position, int gScore, int hScore){
        this.cameFrom = cameFrom;
        this.position = position;
        this.gScore = gScore;
        this.hScore = hScore;
        this.fScore = hScore + gScore;
    }

    public Point getCameFrom() {
        return cameFrom;
    }

    public Point getPosition() {
        return position;
    }

    public int getgScore() {
        return gScore;
    }

    public int getfScore() {
        return fScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorldNode worldNode = (WorldNode) o;

        return position != null ? position.equals(worldNode.position) : worldNode.position == null;
    }

    @Override
    public int hashCode() {
        return position != null ? position.hashCode() : 0;
    }
}
