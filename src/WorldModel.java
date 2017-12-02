import org.omg.CORBA.INTERNAL;
import processing.core.PImage;

import java.util.*;

final class WorldModel
{
   //private
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public int getNumRows() {
      return numRows;
   }

   public int getNumCols() {
      return numCols;
   }

   public Set<Entity> getEntities() {
      return entities;
   }

   public boolean withinBounds(Point pos)
   {
      return pos.getY() >= 0 && pos.getY() < numRows &&
              pos.getX() >= 0 && pos.getX() < numCols;
   }

   public  boolean isOccupied(Point pos)
   {
      return this.withinBounds(pos) &&
              this.getOccupancyCell(pos) != null;
   }

   public  Optional<Entity> findNearest(Point pos, Class<?> type)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : entities)
      {
         if (entity.getClass().equals(type))
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   public Optional<Miner> findNearestMiner(Point pos)
   {
      List<Miner> ofType = new LinkedList<>();
      for (Entity entity : entities)
      {
         if (entity.getClass().equals(Miner.class))
         {
            ofType.add((Miner) entity);
         }
      }

      return nearestMiner(ofType, pos);
   }

   private static Optional<Miner> nearestMiner(List<Miner> miners, Point pos)
   {
      if (miners.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Miner nearest = miners.get(0);
         int nearestDistance;

         if (nearest.isEmtpy())
            nearestDistance = Integer.MAX_VALUE;
         else
            nearestDistance = nearest.getPosition().distanceSquared(pos);

         for (Miner other : miners)
         {
            int otherDistance;

            if (other.isEmtpy())
               otherDistance = Integer.MAX_VALUE;
            else
               otherDistance = other.getPosition().distanceSquared(pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   private static Optional<Entity> nearestEntity(List<Entity> entities,
                                                Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = nearest.getPosition().distanceSquared(pos);

         for (Entity other : entities)
         {
            int otherDistance = other.getPosition().distanceSquared(pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   /*
      Assumes that there is no entity currently occupying the
      intended destination cell.
   */
   public  void addEntity(Entity entity)
   {
      if (this.withinBounds(entity.getPosition()))
      {
         this.setOccupancyCell(entity.getPosition(), entity);
         entities.add(entity);
      }
   }

   private void removeEntityAt(Point pos)
   {
      if (this.withinBounds(pos)
              && this.getOccupancyCell(pos) != null)
      {
         Entity entity = this.getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         this.setOccupancyCell(pos, null);
      }
   }

   private Entity getOccupancyCell(Point pos)
   {
      return occupancy[pos.getY()][pos.getX()];
   }

   private void setOccupancyCell(Point pos, Entity entity)
   {
      occupancy[pos.getY()][pos.getX()] = entity;
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (this.withinBounds(pos))
      {
         return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
      }
      else
      {
         return Optional.empty();
      }
   }

   public void setBackground(Point pos, Background background)
   {
      if (this.withinBounds(pos))
      {
         this.setBackgroundCell(pos, background);
      }
   }

   private Background getBackgroundCell(Point pos)
   {
      return background[pos.getY()][pos.getX()];
   }

   private void setBackgroundCell(Point pos, Background background)
   {
      this.background[pos.getY()][pos.getX()] = background;
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (this.withinBounds(pos) && !pos.equals(oldPos))
      {
         this.setOccupancyCell(oldPos, null);
         this.removeEntityAt(pos);
         this.setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntity(Entity entity)
   {
      this.removeEntityAt(entity.getPosition());
   }

   public void tryAddEntity(Entity entity)
   {
      if (this.isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity(entity);
   }
   //_public Optional<Entity> getOccupant(Point Pos)
   // move to WorldModel
   public Optional<Entity> getOccupant(Point pos)
   {
      if (this.isOccupied(pos))
      {
         return Optional.of(this.getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

}
