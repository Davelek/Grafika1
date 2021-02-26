package utils;

import drawable.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.List;

import static utils.Vypocty.getPolygon;

public class Renderer {
    BufferedImage img;
    private boolean dashed = false;


    public Renderer(BufferedImage img) {
        this.img = img;

    }

    public int getWidth(){
        return img.getWidth();
    }
    public int getHeight(){
        return img.getHeight();
    }

    public int getPixel(int x, int y){
        return img.getRGB(x, y);
    }

    public void drawPixel(int x, int y, Color color1) {
        if (x < 0 || x >= img.getWidth()) return;
        ;
        if (y < 0 || y >= img.getHeight()) return;
        ;
        img.setRGB(x, y, color1.getRGB());
    }

    private void drawPixelDashed(int x, int y, int d, Color color1) {
        if (x < 0 || x >= 800) return;
        ;
        if (y < 0 || y >= 600) return;
        ;
        if (d % 4 < 0) {

        } else {
            dashed = !dashed;
        }
        if (dashed) {
            img.setRGB(x, y, color1.getRGB());
        }


    }

    public void drawDashedLine(int x1, int y1, int x2, int y2, Color color) {

        float[] floats = doDDA(x1, y1, x2, y2);

        int max = (int) floats[0];
        float x = floats[1];
        float y = floats[2];
        float G = floats[3];
        float H = floats[4];
        for (int i = 0; i <= max; i++) {
            drawPixelDashed(Math.round(x), Math.round(y), i, color);
            x = x + G;
            y = y + H;
        }


    }

    public void lineTrivial(int x1, int y1, int x2, int y2, Color color) {
        int dx = x1 - x2;
        int dy = y1 - y2;

        if (Math.abs(dx) > Math.abs(dy)) {



            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;

                temp = y1;
                y1 = y2;
                y2 = temp;

            }


            double k, q;
            k = dy / (double) dx;


            for (int x = x1; x < x2; x++) {
                int y = y1 + (int) (k * (x - x1));
                drawPixel(x, y, color);
            }


        } else {
            //řídící osa y
            if (y1 > y2) {
                // int temp = x1;
                x1 = x2;
                //   x2 = temp;

                int temp = y1;
                y1 = y2;
                y2 = temp;

            }


            double k, q;
            k = dx / (double) dy;


            for (int y = y1; y < y2; y++) {
                int x = x1 + (int) (k * (y - y1));
                drawPixel(x, y, color);
            }

        }
    }


    public void lineDDA(int x1, int y1, int x2, int y2, Color color) {
        float[] floats = doDDA(x1, y1, x2, y2);
        // nechám to redudantí kvůly přehlednosti
        int max = (int) floats[0];
        float x = floats[1];
        float y = floats[2];
        float G = floats[3];
        float H = floats[4];
        for (int i = 0; i <= max; i++) {

            drawPixel(Math.round(x), Math.round(y), color);
            x = x + G;
            y = y + H;
        }


    }

    private float[] doDDA(int x1, int y1, int x2, int y2) {
        float[] helfer = new float[10];
        int dx, dy;
        float x, y, k, G, H;
        dx = x2 - x1;
        dy = y2 - y1;

        k = dy / (float) dx;

        if (Math.abs(dx) > Math.abs(dy)) {
            G = 1;
            H = k;
            if (x1 > x2) {
                //     int temp = x1;
                x1 = x2;
                //    x2 = temp;
                //   temp = y1;
                y1 = y2;
                //       y2 = temp;
            }
        } else {
            G = 1 / k;
            H = 1;

            if (y1 > y2) {
                //    int temp = x1;
                x1 = x2;
                //   x2 = temp;
                //    temp = y1;
                y1 = y2;
                //  y2 = temp;
            }
        }

        x = x1;
        y = y1;

        int max = Math.max(Math.abs(dx), Math.abs(dy));
        helfer[0] = max;
        helfer[1] = x;
        helfer[2] = y;
        helfer[3] = G;
        helfer[4] = H;


        return helfer;
    }


    public void polygon(int x1, int y1, int x2, int y2, int count, Color color) {
        List<Point> points = getPolygon(new Point(x1,y1),new Point(x2,y2),count);
        for (int i = 0; i < points.size()-1; i++) {
            lineDDA(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY(), color);
        }

    }




    public void circle(int x1, int y1, int x2, int y2, Color color) {


        double r = Vypocty.vypocetVzdalenosti(x1, y1, x2, y2);
        r = r * 2;
        int x = x1 - ((int) Math.round(r / 2));
        int y = y1 - ((int) Math.round(r / 2));

        Graphics g = img.getGraphics();
        g.setColor(color);
        g.drawOval(x, y, (int) r, (int) r);

    }

    public void circleSegment(int x1, int y1, int x2, int y2, double alpha, Color color) {

        double r = Vypocty.vypocetVzdalenosti(x1, y1, x2, y2);
        r = r * 2;


        int[] bod = circleHelper(x1, y1, r);
        Graphics g = img.getGraphics();
        g.setColor(color);

        g.drawArc(bod[0], bod[1], (int) r, (int) r, 0, (int) alpha);

    }


    private int[] circleHelper(int x1, int y1, double r) {
        int[] bod = new int[2];
        bod[0] = x1 - ((int) Math.round(r / 2));
        bod[1] = y1 - ((int) Math.round(r / 2));
        return bod;
    }


}


