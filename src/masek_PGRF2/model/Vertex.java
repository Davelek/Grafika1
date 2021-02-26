package masek_PGRF2.model;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.awt.*;

public class Vertex {

    private final Point3D point;
    private final Color color;
    public final double x, y, z, w;
    private Mat4 transform = new Mat4Identity();
    private boolean isTransofmable = true;

    public boolean isTransofmable() {
        return isTransofmable;
    }

    public void setTransofmable(boolean transofmable) {
        isTransofmable = transofmable;
    }

    public Mat4 getTransform() {
        return transform;
    }

    public void setTransform(Mat4 transform) {
        this.transform = transform;
    }

    public Vertex(Point3D point, Color color) {
        this.point = point;
        this.color = color;
        x = point.getX();
        y = point.getY();
        z = point.getZ();
        w = point.getW();
    }

    public Vertex setPoint(Point3D point, Color color) {
        return new Vertex(point, color);
    }

    public Point3D getPoint() {
        return point;
    }

    public Color getColor() {
        return color;
    }
}
