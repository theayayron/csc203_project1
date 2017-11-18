import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AStarPathingStrategy implements PathingStrategy {

    private static final Comparator<WorldNode> byFScore = Comparator.comparing(WorldNode::getfScore);
    private final PriorityQueue<WorldNode> nodesToSearch = new PriorityQueue<>(byFScore);



    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        final ArrayList<WorldNode> closedList = new ArrayList<>();
        final Predicate<WorldNode> notVisited = w -> !closedList.contains(w);

        // add start point to queue
        nodesToSearch.add(new WorldNode (null, start, 0, 0));

        // iterate through queue until all paths are evaluated
        while (!nodesToSearch.isEmpty())
        {
            WorldNode currentNode = nodesToSearch.peek();

            if (withinReach.test(currentNode.getPosition(), end))
                return buildPath();

            potentialNeighbors.apply(currentNode.getPosition())
                    .filter(canPassThrough)
                    .map(p -> new WorldNode(currentNode.getPosition(),
                            p,
                            currentNode.getgScore() + 1,
                            currentNode.getPosition().manhattanDistance(end)))

                    .filter(notVisited)
                    .forEach(w -> nodesToSearch.add(w));

        }
    }

//    private static List<WorldNode> getNeighbors(WorldNode currentNode, Point endPoint,
//                                                Predicate<Point> canPassThrough,
//                                                Predicate<WorldNode> notVisited,
//                                                Function<Point, Stream<Point>> potentialNeighbors)
//    {
//         return potentialNeighbors.apply(currentNode.getPosition())
//                    .filter(canPassThrough)
//                    .map(p -> new WorldNode(currentNode.getPosition(),
//                                        p,
//                                        currentNode.getgScore() + 1,
//                                        currentNode.getPosition().manhattanDistance(endPoint)))
//
//                    .filter(notVisited)
//                    .forEach(w -> nodesToSearch.add());
//    }


}
