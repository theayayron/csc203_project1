import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class AStarPathingStrategy implements PathingStrategy {

    private static final Comparator<WorldNode> byFScore = Comparator.comparing(WorldNode::getfScore);
    private final PriorityQueue<WorldNode> nodesToSearch = new PriorityQueue<>(byFScore);
    private final ArrayList<WorldNode> closedList = new ArrayList<>();

    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        return null;
    }

}
