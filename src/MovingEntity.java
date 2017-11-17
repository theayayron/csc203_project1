import processing.core.PImage;

import java.util.function.BiPredicate;
import java.util.function.Predicate;


import java.util.List;

abstract class MovingEntity extends AbstractAnimatedEntity {

    private static final BiPredicate<Point, Point> WITHIN_REACH = (p1, p2) -> p1.adjacent(p2);
    private static final SingleStepPathingStrategy SINGLE_STEP = new SingleStepPathingStrategy();


    public MovingEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int repeatCount) {
        super(id, position, images, actionPeriod, animationPeriod, repeatCount);
    }

    protected Point nextPosition(WorldModel world, Point destPos) {
        final Predicate<Point> canPassThrough = p -> !world.isOccupied(p);

        final List<Point> nextPos = SINGLE_STEP
                .computePath(super.getPosition(), destPos, canPassThrough,
                        WITHIN_REACH, PathingStrategy.CARDINAL_NEIGHBORS);

        if (nextPos.isEmpty())
            return super.getPosition();

        return nextPos.get(0);
    }

}
