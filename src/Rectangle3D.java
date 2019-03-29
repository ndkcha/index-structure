package quadTreeIndex;

import quadTreeIndex.Point;

/*
 * A cube area divided from one center point
 */

public class Rectangle3D {
    private Point center;
    private double width;

    public Rectangle3D(Point center, double width) {
        this.center = center;
        this.width = width;
    }

    public Rectangle3D(double x, double y, double z, double width) {
        this(new Point(x,y,z), width);
    }

    public Point getCenter() {
        return center;
    }

    public boolean contains(Point point) {
        return  (
                (center.getX() - width/2 <= point.getX()) &&
                (center.getX() + width/2 >= point.getX()) &&
                (center.getY() - width/2 <= point.getY()) &&
                (center.getY() + width/2 >= point.getY()) &&
                (center.getZ() - width/2 <= point.getZ()) &&
                (center.getZ() + width/2 >= point.getZ())
        );
    }

    @Override
    public String toString() {
        return "Cube{" +
                "center=" + center +
                ", width=" + width +
                '}';
    }
}
