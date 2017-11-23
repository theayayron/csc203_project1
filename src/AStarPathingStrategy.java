import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;



public class AStarPathingStrategy implements PathingStrategy {

    private static final Comparator<WorldNode> byFScore = Comparator.comparing(WorldNode::getfScore);
    //private static final Comparator<WorldNode> byGScore = Comparator.comparing(WorldNode::getgScore);


    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        PriorityQueue<WorldNode> nodesToSearch = new PriorityQueue<>(byFScore);
        LinkedList<WorldNode> closedList = new LinkedList<>();
        HashMap<Point, WorldNode> closedL = new HashMap<>();
        LinkedList<Point> path = new LinkedList<>();

        // checks if it's on the list to be searched, updates
        // node in list if new path takes fewer steps
        Predicate<WorldNode> notVisited = w -> !closedList.contains(w);
                                               //closedL.get(w.getPosition()) == null;

        /*{
             if (.contains(w))
                for (WorldNode node : nodesToSearch) {

                    if (node.getPosition().equals(w.getPosition())) {
                        if (byGScore.compare(w, node) < 0) {
                            nodesToSearch.remove(node);
                            return true;
                        } else
                            return false;
                    }
                }
            return !closedList.contains(w);
        };*/

        // add start point to queue
        nodesToSearch.add(new WorldNode (null, start, 0, 0));

        // iterate through queue until all paths are evaluated
        while (!nodesToSearch.isEmpty())
        {
            WorldNode currentNode = nodesToSearch.poll();

            if (withinReach.test(currentNode.getPosition(), end))
                return buildPath(currentNode, closedList, path);

            potentialNeighbors.apply(currentNode.getPosition())
                    .filter(canPassThrough)
                    .map(p -> new WorldNode(currentNode.getPosition(), p,
                                            currentNode.getgScore() + 1,
                                            p.manhattanDistance(end)))

                    .filter(notVisited)
                    .forEach(w -> nodesToSearch.add(w));

            closedList.push(currentNode);
            //closedL.put(currentNode.getPosition(), currentNode);

        }

        return new LinkedList<Point>();

    }

    private static List<Point> buildPath(WorldNode lastNode, HashMap<Point, WorldNode> closedL, LinkedList<Point> path)
    {
        WorldNode currentNode;
        path.push(lastNode.getPosition());

        while( lastNode.getgScore() != 0 )
        {
            currentNode = closedL.get(lastNode.getCameFrom());
            path.push(currentNode.getPosition());
            lastNode = currentNode;

        }

        return path;
    }


    private static List<Point> buildPath(WorldNode lastNode, LinkedList<WorldNode> closedList, LinkedList<Point> path)
    {
        path.push(lastNode.getPosition());

        for (WorldNode currNode : closedList) {
            if(currNode.getgScore() == 0)
                return path;

            if (lastNode.getCameFrom().equals(currNode.getPosition())) {
                path.push(currNode.getPosition());
                lastNode = currNode;
            }
        }

        return path;
    }

}
