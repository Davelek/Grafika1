package drawable;

import utils.Renderer;

import java.awt.*;
import java.util.List;

import drawable.Point;

public interface Drawable {
    void addPoint(Point p);
    void draw(Renderer renderer);
    void count(int count);
    void setColor(Color color);
    List<Point> getPoints();



}
