import java.util.List;

import processing.core.PImage;

public interface Entity
{
   Point getPosition();

   void setPosition(Point position);

   List<PImage> getImages();

   int getImageIndex();

   PImage getCurrentImage();

   String getID();
}
