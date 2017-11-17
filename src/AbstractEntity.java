import processing.core.PImage;

import java.util.List;

public abstract class AbstractEntity implements Entity {

    private String id;
    private Point position;
    private List<PImage> images;
    protected int imageIndex;

    public AbstractEntity(String id, Point position,
                          List<PImage> images, int imageIndex)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = imageIndex;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public List<PImage> getImages() {
        return images;
    }

    @Override
    public int getImageIndex() {
        return imageIndex;
    }

    @Override
    public PImage getCurrentImage() {
        return (images.get(imageIndex));
    }

    @Override
    public String getID() {
        return id;
    }

}
