package filler;

import drawable.Drawable;

import drawable.Point;
import drawable.Polygon;
import utils.Renderer;
import utils.Vypocty;


import java.util.ArrayList;

import java.util.List;


import java.awt.*;

public class ScanLine implements Filler {




    private Drawable nuhelnik;
    private Polygon polygon;
    private List<LineS> lineS;
    private Renderer renderer;
    private Color color;


    public ScanLine(Renderer renderer, Color color, Drawable nuhelnik) {
        this.renderer = renderer;
        this.color = color;
        lineS = new ArrayList<>();
        this.nuhelnik = nuhelnik;
        setBounds(nuhelnik);
    }

    public void setBounds(Drawable nuhelnik){
        this.nuhelnik = nuhelnik;
        for (int i = 0; i < nuhelnik.getPoints().size()-1; i++) {
            lineS.add(new LineS(nuhelnik.getPoints().get(i),nuhelnik.getPoints().get(i+1)));
        }
            lineS.add(new LineS(nuhelnik.getPoints().get(0),nuhelnik.getPoints().get(nuhelnik.getPoints().size()-1)));
        }


    private int maxY(List<Point> list) {
        int max = list.get(0).getY();
        for (int i = 1; i < list.size(); i++) {
            if (max < list.get(i).getY())
                max = list.get(i).getY();
        }

        return max;
    }


    private int minY(List<Point> list) {
        int min = list.get(0).getY();
        for (int i = 1; i < list.size(); i++) {
            if (min > list.get(i).getY())
                min = list.get(i).getY();
        }

        return min;
    }



    private void fillAll(boolean patern){
        List<Point> list = nuhelnik.getPoints();
        int maxY = maxY(list);
        int minY = minY(list);
        for (int y = minY; y < maxY; y++) {
            List<Integer> intersection = new ArrayList<>();

            for (LineS line : lineS) {

                if (line.isIntersection(y)){
                    boolean kontrola = true;
                    for (int help:intersection
                         ) {
                        if (help == line.getIntersection(y)){
                            kontrola = false;
                        }
                    }
                    if (kontrola)
                    intersection.add(line.getIntersection(y));
                }
            }



            List<Integer> sortedIntersection = Vypocty.bubbleSort(intersection);




            for (int i = 0; i < sortedIntersection.size()-1; i=i+2) {


                if (!patern)
                renderer.lineDDA(sortedIntersection.get(i),y,sortedIntersection.get(i+1),y,color);

                if (patern){
                    if (y%3 == 0)
                        renderer.lineDDA(sortedIntersection.get(i),y,sortedIntersection.get(i+1),y,color);
                }

            }

        }

    }

    public void fill(boolean pattern){
        fillAll(pattern);
    }

    @Override
    public void fill() {
        fillAll(false);
    }

    @Override
    public int getColor() {
        return color.getRGB();
    }

    @Override
    public void setColor(Color Color) {
        this.color = color;
    }

    @Override
    public void setRaster(Renderer renderer) {
        this.renderer = renderer;
    }


}
