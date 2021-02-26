package drawable;

import utils.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Circle implements Drawable {


    private Color color;
    private Point point1;
    private Point point2;
    private Boolean moznoUdelat = false;

    public Circle(Color color, Point point1) {
        this.color = color;
        this.point1 = point1;
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
        point2 = p;
        moznoUdelat = true;
    }

    @Override
    public void draw(Renderer renderer) {
        if (moznoUdelat)
            renderer.circle(point1.getX(), point1.getY(), point2.getX(), point2.getY(), color);

    }

    @Override
    public void count(int count) {

    }

    @Override
    public void setColor(Color color) {

    }


}
