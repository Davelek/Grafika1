package filler;

import drawable.Point;

public class LineS{
    private Point a,b;


    public LineS(Point a, Point b) {
        if (a.getY() > b.getY()){
            this.a = b;
            this.b = a;

        }else {

            this.a = a;
            this.b = b;
        }
    }


    public boolean isIntersection(int y) {
        return (a.getY() <= y && b.getY() >= y);
    }

    public int getIntersection(int y) {


        int x1 = a.getX();
        int x2 = b.getX();
        int y1 = a.getY();
        int y2 = b.getY();


       int dx = x2 - x1;
       int dy = y2 - y1;

       float k = dy / (float) dx;
       float b = y1 -k*x1;

       int x = Math.round((y-b)/k);
     if ((x1 > x && x2 > x) )
        return x1;

        return x;




    }
}