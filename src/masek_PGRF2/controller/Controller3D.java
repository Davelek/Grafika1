package masek_PGRF2.controller;

import masek_PGRF2.model.Element;
import masek_PGRF2.model.ElementType;
import masek_PGRF2.model.Vertex;

import masek_PGRF2.renderer.GPURenderer;
import masek_PGRF2.renderer.Renderer3D;
import masek_PGRF2.view.Raster;
import transforms.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Controller3D {

    private GPURenderer renderer3D;
    private Camera camera;

    private int click;
    private int mousePositionX;
    private int mousePositionY;
    private int newMousePosX;
    private int newMousePosY;
    private boolean fill = true;
    private boolean perspektiv = true;
    private int mx, my;
    private int clickX, clickY, klick = 0;

    private List<Element> elements;
    private List<Vertex> vb;
    private List<Integer> ib;

    public Controller3D(Raster raster) {
        initObjects(raster);
        initListeners(raster);
    }

    private void display() {
        renderer3D.clear();

        renderer3D.setModel(new Mat4Identity());
        renderer3D.setView(camera.getViewMatrix());


        renderer3D.draw(elements, vb, ib, fill);

        renderer3D.setModel(new Mat4Transl(5, 0, 0));

    }

    private void initObjects(Raster raster) {
        renderer3D = new Renderer3D(raster);
        resetCamera();


        ib = new ArrayList<>();
        vb = new ArrayList<>();
        elements = new ArrayList<>();


        elements.add(new Element(ElementType.TRIANGLE, new Cube().count, 0));
        elements.add(new Element(ElementType.LINE, new sipkaX().count, 36));
        elements.add(new Element(ElementType.LINE, new sipkaY().count, 36 + 18));
        elements.add(new Element(ElementType.LINE, new sipkaZ().count, 36 + 18 + 18));
        elements.add(new Element(ElementType.TRIANGLE, new Jehlan().count, 36 + 18 + 18 + 18));

        new Cube(vb, ib, 0);
        new sipkaX(vb, ib, 14, Color.red);
        new sipkaY(vb, ib, 20, Color.GREEN);
        new sipkaZ(vb, ib, 26, Color.blue);
        new Jehlan(vb, ib, 26 + 6);

        Mat4Transl mat4Scale6 = new Mat4Transl(2, 1, 0);
        for (int i = vb.size() - 5; i < vb.size(); i++) {
            vb.get(i).setTransform(vb.get(i).getTransform().mul(mat4Scale6));
        }

        Mat4Transl mat4Scale2 = new Mat4Transl(-2, -1, 0);
        for (int i = 0; i <= 13; i++) {
            vb.get(i).setTransform(vb.get(i).getTransform().mul(mat4Scale2));
        }

        renderer3D.draw(elements, vb, ib, fill);


    }

    private void resetCamera() {
        camera = new Camera().withPosition(new Vec3D(0, -6, 0))
                .withAzimuth(Math.toRadians(90)).withZenith(Math.toRadians(0));

        renderer3D.setView(camera.getViewMatrix());
    }

    private void initListeners(Raster raster) {
        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                    mx = e.getX();
                    my = e.getY();
                    klick = 1;
                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    clickX = e.getX();
                    clickY = e.getY();
                    klick = 2;


                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                klick = 0;
            }
        });


        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent f) {

                if (klick == 1) {


                    for (Vertex vertex : vb
                    ) {
                        Mat4RotXYZ rot = new Mat4RotXYZ(-(mx - f.getX()) * 0.02, -(f.getY() - my) * (0.02), 0);
                        if (vertex.isTransofmable())
                            vertex.setTransform(vertex.getTransform().mul(rot));


                    }
                    display();
                    mx = f.getX();
                    my = f.getY();
                }
                if (klick == 2) {
                    camera = camera.addAzimuth((f.getX() - clickX) * (0.002)).addZenith((f.getY() - clickY) * (0.002));
                    clickX = f.getX();
                    clickY = f.getY();
                    display();

                }
            }


        });


        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_P:

                        camera = new Camera().withPosition(new Vec3D(0, -6, 0))
                                .withAzimuth(Math.toRadians(90)).withZenith(Math.toRadians(0));

                        renderer3D.setView(camera.getViewMatrix());


                        if (perspektiv)
                            renderer3D.setProjection(new Mat4OrthoRH(5, 5, 0.01, 15));

                        else
                            renderer3D.setProjection(new Mat4PerspRH(0.7853981633974483D, ((float) raster.getHeight() / (float) raster.getWidth()), 0.01D, 100.0D));
                        perspektiv = !perspektiv;
                        display();
                        break;
                    case KeyEvent.VK_UP:
                        camera = camera.up(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_W:

                        camera = camera.forward(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_DOWN:
                        camera = camera.down(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_M:
                        fill = !fill;
                        display();
                        break;
                    case KeyEvent.VK_S:
                        camera = camera.backward(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_RIGHT:
                        camera = camera.right(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_D:

                        camera = camera.right(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_LEFT:
                        camera = camera.left(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_E:
                        camera = camera.addAzimuth(-0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_Q:
                        camera = camera.addAzimuth(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_SUBTRACT:
                        Mat4Scale mat4Scale = new Mat4Scale(0.8, 0.8, 0.8);
                        for (Vertex vertex : vb
                        ) {
                            if (vertex.isTransofmable()) {
                                vertex.setTransform(vertex.getTransform().mul(mat4Scale));
                            }
                        }

                        display();
                        break;
                    case KeyEvent.VK_ADD:
                        Mat4Scale mat4Scale2 = new Mat4Scale(1.2, 1.2, 1.2);
                        for (Vertex vertex : vb
                        ) {
                            if (vertex.isTransofmable())
                                vertex.setTransform(vertex.getTransform().mul(mat4Scale2));
                        }
                        display();
                        break;
                    case KeyEvent.VK_A:
                        camera = camera.left(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;


                    case KeyEvent.VK_NUMPAD1:
                        camera = camera.addAzimuth(-0.01);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_NUMPAD2:
                        camera = camera.addZenith(-0.01);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_NUMPAD4:
                        camera = camera.addAzimuth(0.01);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_NUMPAD5:
                        camera = camera.addZenith(0.01);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                }
            }
        });
    }

}
