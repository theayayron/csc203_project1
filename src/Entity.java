import java.util.List;

import processing.core.PImage;

public interface Entity
{
   Point getPosition();

   void setPosition(Point position);

   List<PImage> getImages();

   int getImageIndex();

   <R> R accept(EntityVisitor<R> visitor);

   PImage getCurrentImage();

   String getID();
}
