package drawable;

import utils.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Vypocty.getPolygon;

public class Polygon implements Drawable {
    private Point point;
    private Point point2;
    private int count;
    private boolean moznoUdelat = false;
    private Color color;
    private Renderer renderer;
    private List<Point> points;

    public Polygon(Point point, Color color, Renderer renderer) {
        this.point = point;
        this.color = color;
        this.renderer = renderer;
        points = new ArrayList<>();

    }

    @Override
    public List<Point> getPoints() {
        return getPolygon(point,point2,count);
    }

    @Override
    public void addPoint(Point p) {
        point2 = p;
       /* for (int i = 0; i < renderer.getPolygon(point,point2,count).size(); i++) {
            points.add(renderer.getPolygon(point,point2,count).get(i));
        }*/
        /*
        points = renderer.getPolygon(point,point2,count);*/
        moznoUdelat = true;

    }

    @Override
    public void count(int count) {
        this.count = count;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }


    @Override
    public void draw(Renderer renderer) {
        if (moznoUdelat) {
            renderer.polygon(point.getX(), point.getY(), point2.getX(), point2.getY(), count, color);
        }

    }
}
