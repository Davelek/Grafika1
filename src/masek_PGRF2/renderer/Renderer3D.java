package masek_PGRF2.renderer;

import masek_PGRF2.helper.Utils;
import masek_PGRF2.model.Element;
import masek_PGRF2.model.ElementType;
import masek_PGRF2.model.Vertex;
import masek_PGRF2.view.Raster;
import transforms.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;


public class Renderer3D implements GPURenderer {

    private final Raster raster;
    private Mat4 model, view;
    private Mat4 projection;
    private boolean fill = true;

    private ZBuffer<Double> zBuffer;

    public Renderer3D(Raster raster) {
        this.raster = raster;

        model = new Mat4Identity();
        view = new Mat4Identity();

        projection = new Mat4PerspRH(0.7853981633974483D, ((float) this.raster.getHeight() / (float) this.raster.getWidth()), 0.01D, 100.0D);

        zBuffer = new ZBuffer<>(new Double[Raster.WIDTH][Raster.HEIGHT]);
        zBuffer.clear(1d);
    }


    @Override
    public void draw(List<Element> elements, List<Vertex> vb, List<Integer> ib, boolean fill) {
        this.fill = fill;
        for (Element element : elements) {
            final int start = element.getStart();
            final int count = element.getCount();
            if (element.getElementType() == ElementType.TRIANGLE) {
                for (int i = start; i < count + start; i += 3) {

                    final Integer i1 = ib.get(i);
                    final Integer i2 = ib.get(i + 1);
                    final Integer i3 = ib.get(i + 2);
                    final Vertex v1 = vb.get(i1);
                    final Vertex v2 = vb.get(i2);
                    final Vertex v3 = vb.get(i3);

                    prepareTriangle(v1, v2, v3);

                }
            } else if (element.getElementType() == ElementType.LINE) {
                for (int i = start; i < count + start; i += 2) {
//
                    final Integer i1 = ib.get(i);
                    final Integer i2 = ib.get(i + 1);
                    final Vertex v1 = vb.get(i1);
                    final Vertex v2 = vb.get(i2);
//
                    prepareLine(v1, v2);
                }
            } else {

            }
        }
    }


    private void prepareLine(Vertex a, Vertex b) {
        a = new Vertex(a.getPoint().mul(a.getTransform()).mul(view).mul(projection), a.getColor());
        b = new Vertex(b.getPoint().mul(b.getTransform()).mul(view).mul(projection), b.getColor());
        if (-a.w > a.x && -b.w > b.x) return;
        if (a.x > a.w && b.x > b.w) return;

        // TODO y, z - HOTOVO
        if (-a.w > a.y && -b.w > b.y) return;
        if (a.y > a.w && b.y > b.w) return;

        if (0 > a.z && 0 > b.z) return;
        if (a.z > a.w && b.z > b.w) return;

        if (a.z < b.z) {
            Vertex temp = a;
            a = b;

            b = temp;
        }

        if (a.z < 0) {

            return;
        } else if (b.z < 0) {

            double t = (0 - a.z) / (b.z - a.z);
            Vertex ab = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), b.getColor());

            new RenderLine(raster).drawLine(a, ab, a.getColor());
        } else {

            new RenderLine(raster).drawLine(a, b, a.getColor());
        }


    }

    private void prepareTriangle(Vertex a, Vertex b, Vertex c) {

        a = new Vertex(a.getPoint().mul(a.getTransform()).mul(view).mul(projection), a.getColor());
        // TODO b, c - HOTOVO
        b = new Vertex(b.getPoint().mul(b.getTransform()).mul(view).mul(projection), b.getColor());
        c = new Vertex(c.getPoint().mul(c.getTransform()).mul(view).mul(projection), c.getColor());


        if (-a.w > a.x && -b.w > b.x && -c.w > c.x) return;
        if (a.x > a.w && b.x > b.w & c.x > c.w) return;

        // TODO y, z - HOTOVO
        if (-a.w > a.y && -b.w > b.y && -c.w > c.y) return;
        if (a.y > a.w && b.y > b.w && c.y > c.w) return;

        if (0 > a.z && 0 > b.z && 0 > c.z) return;
        if (a.z > a.w && b.z > b.w && c.z > c.w) return;


        if (a.z < b.z) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        if (b.z < c.z) {
            Vertex temp = b;
            b = c;
            c = temp;
        }
        if (a.z < b.z) {
            Vertex temp = a;
            a = b;
            b = temp;
        }

        if (a.z < 0) {

            return;
        } else if (b.z < 0) {

            double t = a.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
            Vertex ab = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), b.getColor());
            // nekorektně barva, měla by se také interpolovat; volitelně


            double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());
            // lze vytvořit funkci pro ořezání, aby se neopakoval kód
            System.out.println("Jdu kreslit 1");
            if (fill)
                drawTriangle(a, ab, ac);
            else
                new RenderLine(raster).prepareTriangleWithnoutFill(a, ab, ac);
        } else if (c.z < 0) {
            // TODO ac, bc - HOTOVO

            double t = b.getPoint().getZ() / (b.getPoint().getZ() - c.getPoint().getZ());
            Vertex bc = new Vertex(b.getPoint().mul(1 - t).add(c.getPoint().mul(t)), c.getColor());

            double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());
            System.out.println("Jdu kreslit 2");
            if (fill) {
                drawTriangle(a, b, ac);
                drawTriangle(b, ac, bc);
            } else {
                new RenderLine(raster).prepareTriangleWithnoutFill(a, b, ac);
                new RenderLine(raster).prepareTriangleWithnoutFill(b, ac, bc);


            }
        } else {
            if (fill)
                drawTriangle(a, b, c);
            else
                new RenderLine(raster).prepareTriangleWithnoutFill(a, b, c);
        }
    }


    private void drawTriangle(Vertex a, Vertex b, Vertex c) {
        if (Utils.fastCut(a.getPoint()) && Utils.fastCut(b.getPoint()) && Utils.fastCut(c.getPoint())) {

            Color c1 = a.getColor();
            Color c2 = b.getColor();
            Color c3 = c.getColor();

            Optional<Vec3D> d1 = a.getPoint().dehomog();
            Optional<Vec3D> d2 = b.getPoint().dehomog();
            Optional<Vec3D> d3 = c.getPoint().dehomog();
            // zahodit trojúhelník, pokud některý vrchol má w==0 (nelze provést dehomogenizaci)
            if (!d1.isPresent() || !d2.isPresent() || !d3.isPresent()) return;

            Vec3D v1 = d1.get();
            Vec3D v2 = d2.get();
            Vec3D v3 = d3.get();


            v1 = transformToWindow(v1);
            v2 = transformToWindow(v2);
            v3 = transformToWindow(v3);
            if (v1.getY() > v2.getY()) {
                Vec3D temp = v1;
                v1 = v2;
                v2 = temp;

                Color tempC = c1;
                c1 = c2;
                c2 = tempC;
            }
            if (v2.getY() > v3.getY()) {
                Vec3D temp = v2;
                v2 = v3;
                v3 = temp;

                Color tempC = c2;
                c2 = c3;
                c3 = tempC;
            }
            if (v1.getY() > v2.getY()) {
                Vec3D temp = v1;
                v1 = v2;
                v2 = temp;

                Color tempC = c1;
                c1 = c2;
                c2 = tempC;
            }


            // TODO upravit cyklus
            // TODO dodělat barvy

            float fraction = 0f;
            float pridavani = 0f;
            float pridavani2 = 0f;
            for (int y = (int) (v1.getY() + 1); y < v2.getY(); y++) {
                pridavani++;
            }
            pridavani = 1 / pridavani;

            for (int y = (int) (v2.getY() + 1); y < v3.getY(); y++) {
                pridavani2++;
            }
            pridavani2 = 1 / pridavani2;


            for (int y = (int) (v1.getY() + 1); y < v2.getY(); y++) {
                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
                double t2 = (y - v1.getY()) / (v3.getY() - v1.getY());


                Vec3D vAB = v1.mul(1 - t1).add(v2.mul(t1));

                Vec3D vAC = v1.mul(1 - t2).add(v3.mul(t2));

                if (vAB.getX() > vAC.getX()) {
                    Vec3D temp = vAB;
                    vAB = vAC;
                    vAC = temp;

                }
                if (fill)
                    this.fillLine(y, vAB, vAC, interpolateColor(c1, c3, fraction), interpolateColor(c3, c1, fraction));
                fraction += pridavani;
            }

            fraction = 0f;

            for (int y = (int) (v2.getY() + 1); y < v3.getY(); y++) {
                double t1 = (y - v2.getY()) / (v3.getY() - v2.getY());
                double t2 = (y - v1.getY()) / (v3.getY() - v1.getY());

                Vec3D vBC = v2.mul(1 - t1).add(v3.mul(t1));
                Vec3D vAC = v1.mul(1 - t2).add(v3.mul(t2));

                if (vBC.getX() > vAC.getX()) {
                    Vec3D temp = vBC;
                    vBC = vAC;
                    vAC = temp;
                }
                if (fill)
                    this.fillLine(y, vBC, vAC, interpolateColor(c3, c2, fraction), interpolateColor(c2, c3, fraction));
                fraction += pridavani2;

            }


        }

    }


    private void fillLine(int y, Vec3D a, Vec3D b, Color cA, Color cB) {

        if (a.getX() > b.getX()) {
            Vec3D temp = a;
            a = b;
            b = temp;

            Color tempC = cA;
            cA = cB;
            cB = tempC;
        }


        int aX = 0;
        int bX = 800;
        if (a.getX() > 0) {
            aX = (int) Math.round(a.getX());
        }
        if (b.getX() < 800) {
            bX = (int) b.getX();
        }

        int count = 0;
        for (int x = aX; x < bX; x++) {
            count++;
        }
        float pridavani = 1f / count;
        float fraction = 0f;


        for (int x = aX; x < bX; x++) {
            double t = (x - aX) / (bX - a.getX());
            double z = a.getZ() * (1 - t) + b.getZ() * t;

            drawPixel(x - 1, y - 1, z, interpolateColor(cA, cB, fraction));
            fraction += pridavani;

        }

    }

    public void drawPixel(int x, int y, double z, Color color) {

        if (zBuffer.get(x, y) != null && z < zBuffer.get(x, y)) {

            zBuffer.set(z, x, y);
            raster.drawPixel(x, y, color.getRGB());

        }

    }

    public static Vec3D transformToWindow(Vec3D v) {
        return v.mul(new Vec3D(1, -1, 1)) // Y jde nahoru, chceme dolu
                .add(new Vec3D(1, 1, 0)) // (0,0) je uprostřed, chceme v rohu
                // máme <0, 2> -> vynásobíme polovinou velikosti plátna
                .mul(new Vec3D(Raster.WIDTH / 2f, Raster.HEIGHT / 2f, 1));
    }

    @Override
    public void clear() {
        raster.clear();
        zBuffer.clear(1d);
    }

    @Override
    public Mat4 getModel() {
        return model;
    }

    @Override
    public void setModel(Mat4 model) {
        this.model = model;
    }

    @Override
    public Mat4 getView() {
        return view;
    }

    @Override
    public void setView(Mat4 view) {
        this.view = view;
    }

    @Override
    public Mat4 getProjection() {
        return projection;
    }

    @Override
    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

    @Override
    public void setShader(Shader<Vertex, Color> shader) {

    }

    private java.awt.Color interpolateColor(Color color1, Color color2, float fraction) {
        float intToFloatConst = 1f / 255f;
        fraction = Math.min(fraction, 1f);
        fraction = Math.max(fraction, 0f);

        float red1 = color1.getRed() * intToFloatConst;
        float green1 = color1.getGreen() * intToFloatConst;
        float blue1 = color1.getBlue() * intToFloatConst;
        float alpha1 = color1.getAlpha() * intToFloatConst;

        float red2 = color2.getRed() * intToFloatConst;
        float green2 = color2.getGreen() * intToFloatConst;
        float blue2 = color2.getBlue() * intToFloatConst;
        float alpha2 = color2.getAlpha() * intToFloatConst;

        float deltaRed = red2 - red1;
        float deltaGreen = green2 - green1;
        float deltaBlue = blue2 - blue1;
        float deltaAlpha = alpha2 - alpha1;

        float red = red1 + (deltaRed * fraction);
        float green = green1 + (deltaGreen * fraction);
        float blue = blue1 + (deltaBlue * fraction);
        float alpha = alpha1 + (deltaAlpha * fraction);
        red = Math.min(red, 1f);
        red = Math.max(red, 0f);
        green = Math.min(green, 1f);
        green = Math.max(green, 0f);
        blue = Math.min(blue, 1f);
        blue = Math.max(blue, 0f);
        alpha = Math.min(alpha, 1f);
        alpha = Math.max(alpha, 0f);
        return new Color(red, green, blue, alpha);

    }

}

