public class WorldNode {

    private Point cameFrom;
    private Point position;
    private int gScore;
    private int fScore;

    public WorldNode(Point cameFrom, Point position, int gScore, int fScore) {
        this.cameFrom = cameFrom;
        this.position = position;
        this.gScore = gScore;
        this.fScore = fScore;
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
}
