import java.util.List;
import java.util.LinkedList;

final class Point
{
   //private
   private final int x;
   private final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public boolean adjacent(Point p)
   {
      return (x == p.getX() && Math.abs(y - p.getY()) == 1) ||
              (y == p.getY() && Math.abs(x - p.getX()) == 1);
   }
   
   public  int distanceSquared(Point p)
   {
      int deltaX = x - p.getX();
      int deltaY = y - p.getY();

      return deltaX * deltaX + deltaY * deltaY;
   }

   public int manhattanDistance(Point p)
   {
      int deltaX = Math.abs(x - p.getX());
      int deltaY = Math.abs(y - p.getY());
      return deltaX + deltaY;
   }

   public List<Point> squareNeighbors(int radius) {
      LinkedList<Point> points = new LinkedList<Point>();
      int maxX = x + radius;
      int minY = y - radius;
      for (int minX = x - radius; minX <= maxX; minX++)
      {
         for (int maxY = y + radius; maxY >= minY; maxY--)
         {
            points.add(new Point(minX, maxY));
         }
      }
      return points;
   }
}
