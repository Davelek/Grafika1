package masek_PGRF2.renderer;

import masek_PGRF2.helper.Utils;
import masek_PGRF2.model.Vertex;
import masek_PGRF2.view.Raster;
import transforms.Vec3D;

import javax.rmi.CORBA.Util;
import java.awt.*;
import java.util.Optional;


public class RenderLine extends Renderer3D {


    public RenderLine(Raster raster) {
        super(raster);
    }

    public void prepareTriangleWithnoutFill(Vertex v1, Vertex v2, Vertex v3) {
        if (Utils.fastCut(v1.getPoint())&& Utils.fastCut(v2.getPoint()) && Utils.fastCut(v3.getPoint())) {

            drawLine(v1, v2, Color.white);
            drawLine(v2, v3, Color.white);
            drawLine(v3, v1, Color.white);
        }
    }

    public void drawLine(Vertex a, Vertex b, Color color) {
        if (Utils.fastCut(a.getPoint())&& Utils.fastCut(b.getPoint())){


        Optional<Vec3D> d1 = a.getPoint().dehomog();
        Optional<Vec3D> d2 = b.getPoint().dehomog();

        // zahodit trojúhelník, pokud některý vrchol má w==0 (nelze provést dehomogenizaci)
        if (!d1.isPresent() || !d2.isPresent()) return;

        Vec3D v1 = d1.get();
        Vec3D v2 = d2.get();


        v1 = transformToWindow(v1);
        v2 = transformToWindow(v2);


        DDA(v1, v2, color);
    }

    }

    private void DDA(Vec3D v1, Vec3D v2, Color c1) {

        int dx, dy;
        boolean ridiciX;
        float x, y, k, G, H;
        int x1 = (int) Math.round(v1.getX());
        int y1 = (int) Math.round(v1.getY());

        int x2 = (int) Math.round(v2.getX());
        int y2 = (int) Math.round(v2.getY());
        dx = x2 - x1;
        dy = y2 - y1;
        k = dy / (float) dx;

        if (Math.abs(dx) > Math.abs(dy)) {
            ridiciX = true;
            G = 1;
            H = k;
            if (x1 >= x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        } else {
            ridiciX = false;
            G = 1 / k;
            H = 1;

            if (y1 > y2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        }

        x = x1;
        y = y1;
        int max = Math.max(Math.abs(dx), Math.abs(dy));


        for (int i = 0; i <= max; i++) {
            if (ridiciX) {
                double t1 = (x - x1) / (x2 - x1);
                Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));

                drawPixel(Math.round(x), Math.round(y), vertexAB.getZ(), c1);
            } else {
                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
                Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));
                drawPixel(Math.round(x), Math.round(y), vertexAB.getZ(), c1);
            }

            x = x + G;
            y = y + H;
        }
        double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
        Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));
        drawPixel(Math.round(x), Math.round(y), vertexAB.getZ(), c1);
    }


}
