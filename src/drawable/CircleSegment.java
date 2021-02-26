package drawable;

import utils.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CircleSegment implements Drawable {

    private Color color;
    private Point point1;
    private Point point2;
    private double alpha;
    private Boolean moznoUdelat = false;

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public CircleSegment() {

    }


    @Override
    public List<Point> getPoints() {
        List<Point> list = new ArrayList<>();
        list.add(point1);
        list.add(point2);
        return list;
    }

    @Override
    public void addPoint(Point p) {
        this.point2 = p;

        moznoUdelat = true;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public void draw(Renderer renderer) {
        if (moznoUdelat)
            renderer.circleSegment(point1.getX(), point1.getY(), point2.getX(), point2.getY(), alpha, color);
    }

    @Override
    public void count(int count) {

    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }


    public Point getPoint1() {
        return point1;
    }
}
