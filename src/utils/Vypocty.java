package utils;

import drawable.Point;

import java.util.ArrayList;
import java.util.List;

public class Vypocty {

    public static double vypocetVzdalenosti(int x1, int y1, int x2, int y2) {


        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));

    }

    public static List<Point> getPolygon(Point point1, Point point2, int count){
        int x1 = point1.getX();
        int y1 = point1.getY();
        int x2 = point2.getX();
        int y2 = point2.getY();
        double x0 = x2 - x1;
        double y0 = y2 - y1;
        double circleRadius = 2 * Math.PI;
        List<Point> points = new ArrayList<>();
        double step = circleRadius / (double) count;
        for (double i = 0; i < circleRadius; i += step) {

            double x = x0 * Math.cos(step) + y0 * Math.sin(step);
            double y = y0 * Math.cos(step) - x0 * Math.sin(step);


            //lineDDA((int) x0 + x1, (int) y0 + y1, (int) x + x1, (int) y + y1, color);
            points.add(new Point((int)x0+x1,(int)y0+y1));
            points.add(new Point((int)x+x1,(int)y+y1));

            x0 = x;
            y0 = y;

        }

        return points;
    }


    public static List<Integer> bubbleSort(List<Integer> points){
        int temp;
        boolean is_sorted;

        for (int i = 0; i < points.size(); i++) {

            is_sorted = true;

            for (int j = 1; j < (points.size() - i); j++) {


                    if (points.get(j-1) > points.get(j)) {
                        temp = points.get(j-1);
                        points.set(j-1,points.get(j));
                        points.set(j,temp);
                        is_sorted = false;
                    }




            }

            // is sorted? then break it, avoid useless loop.
            if (is_sorted) break;

        }
        return points;
    }


}
