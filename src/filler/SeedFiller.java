package filler;

import utils.Renderer;

import java.awt.*;

import drawable.Point;


public class SeedFiller implements Filler {


    private Color color;
    private Renderer renderer;
    private Point seed;

    private Color bgColor;


    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }


    public SeedFiller(Renderer renderer, Color color, Point point) {
        setRaster(renderer);
        setSeed(new Point(point.getX(),point.getY()));
        this.color = color;
    }


    public void setSeed(Point point){

        setBgColor(new Color(renderer.getPixel(point.getX(),point.getY())));
        this.seed = point;
    }


    private void    seedFill(int startX, int z) {
        int x = startX;
        int y = z;
        int leftX = 0;
        int rightX = renderer.getWidth()-1;





        for (int i = x; i >= 0; i--) {
            leftX = i;
            if (renderer.getPixel(i,y) != bgColor.getRGB()){

                break;
            }
        }

        for (int i = x; i < renderer.getWidth() ; i++) {
            rightX = i;
            if (renderer.getPixel(i,y) != bgColor.getRGB()){

                break;
            }
        }

        renderer.lineDDA(leftX,y,rightX,y,color);
            for (int i = leftX+1; i < rightX-1; i++) {
                if (y+1 < renderer.getHeight() && renderer.getPixel(i,(y+1)) == bgColor.getRGB()){

                    x=i;
                    seedFill(i,y+1);
                }
                if (y-1 >=0 && renderer.getPixel(i, y-1) == bgColor.getRGB()){
                    seedFill(i,y-1);
                }

            }




    }



        @Override
    public void fill() {
            seedFill(seed.getX(),seed.getY());
    }


    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setRaster(Renderer renderer) {
        this.renderer = renderer;
    }
}
