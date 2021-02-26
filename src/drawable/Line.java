package drawable;

import utils.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Line implements Drawable {

    private int x1, y1, x2, y2;
    private Color color;

    @Override
    public void count(int count) {

    }


    @Override
    public List<Point> getPoints() {
        List<Point> list = new ArrayList<>();
        list.add(new Point(x1,y1));
        list.add(new Point(x2, y2));
        return list;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public Line(Point p, Color color) {
        this.y1 = p.getY();
        this.color = color;
        this.x1 = p.getX();
        this.x2 = 0;
        this.y2 = 0;

    }


    @Override
    public void draw(Renderer renderer) {
        // todo
        if (x2 != 0 && y2 != 0)
            renderer.lineDDA(x1, y1, x2, y2, color);

    }

    @Override
    public void addPoint(Point p) {
        this.y2 = p.getY();
        this.x2 = p.getX();
    }
}

