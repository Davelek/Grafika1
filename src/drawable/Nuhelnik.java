package drawable;

import utils.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Nuhelnik implements Drawable {

    private List<Point> points;
    private int index = -1;
    private Color color;

    @Override
    public void count(int count) {

    }


    @Override
    public List<Point> getPoints() {
        return points;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }


    public Nuhelnik(Color color) {
        points = new ArrayList<>();
        this.color = color;
    }

    public void addPoint(Point p) {
        points.add(p);
        index++;
    }

    @Override
    public void draw(Renderer renderer) {

        if (index > 0) {
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    renderer.lineDDA(points.get(i).getX(), points.get(i).getY(), points.get(0).getX(), points.get(0).getY(), color);

                } else {
                    renderer.lineDDA(points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY(), color);
                }
            }
        }


    }
}

