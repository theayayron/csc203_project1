import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class AStarPathingStrategy implements PathingStrategy {

    private static final Comparator<WorldNode> byFScore = Comparator.comparing(WorldNode::getfScore);
    private static final Comparator<WorldNode> byGScore = Comparator.comparing(WorldNode::getgScore);



    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        PriorityQueue<WorldNode> nodesToSearch = new PriorityQueue<>(byFScore);
        LinkedList<WorldNode> closedList = new LinkedList<>();
        LinkedList<Point> path = new LinkedList<>();

        // checks if it's on the list to be searched, updates
        // node in list if new path takes fewer steps
        Predicate<WorldNode> notVisited = w ->
        {
             if (nodesToSearch.contains(w))
                for (WorldNode node : nodesToSearch) {

                    if (node == w) {
                        if (byGScore.compare(w, node) < 0) {
                            nodesToSearch.remove(node);
                            return true;
                        } else
                            return false;
                    }
                }

            return true;

        }; //!closedList.contains(w);

        // add start point to queue
        nodesToSearch.add(new WorldNode (null, start, 0, 0));

        // iterate through queue until all paths are evaluated
        while (!nodesToSearch.isEmpty())
        {
            WorldNode currentNode = nodesToSearch.peek();

            if (withinReach.test(currentNode.getPosition(), end))
                return buildPath(currentNode, closedList, path);

            potentialNeighbors.apply(currentNode.getPosition())
                    .filter(canPassThrough)
                    .map(p -> new WorldNode(currentNode.getPosition(), p,
                                            currentNode.getgScore() + 1,
                                            currentNode.getPosition().manhattanDistance(end)))

                    .filter(notVisited)
                    .forEach(w -> nodesToSearch.add(w));

            closedList.add(currentNode);

        }

        return new LinkedList<Point>();

    }

    private static List<Point> buildPath(WorldNode lastNode, List<WorldNode> closedList, LinkedList<Point> path)
    {
        if (lastNode.getgScore() == 0)
            return path;
        else if (lastNode.getCameFrom() == closedList.get(0).getPosition())
            path.add(closedList.get(0).getPosition());

        WorldNode nextNode = closedList.remove(0);
        return buildPath(nextNode, closedList, path);
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
