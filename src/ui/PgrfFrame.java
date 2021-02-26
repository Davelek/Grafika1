package ui;

import drawable.*;
import drawable.Point;
import drawable.Polygon;
import filler.Filler;
import filler.ScanLine;
import filler.SeedFiller;
import message.Message;
import utils.Vypocty;
import utils.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PgrfFrame extends JFrame implements MouseMotionListener {
    private BufferedImage img;
    private JPanel panel;
    private Renderer renderer;
    private int coorX, coorY;
    private int clickX = 300;
    private int clickY = 300;
    private int count = 5;
    private Boolean firstClick = true;
    private int startX;
    private int startY;
    private int seedIndex = -1;
    private int index = -1;
    private DrawableType type = DrawableType.N_OBJECT;
    private List<Drawable> drawables;
    private List<Filler> fillers;
    private Color color = new Color(255, 255, 255);
    private CircleSegment circleSegment = new CircleSegment();
    private int searchIndex = 0;
    private ScanLine tempScanLine;


    public static void main(String... args) {

        //help();
        PgrfFrame pgrfFrame = new PgrfFrame();
        int width = 800;
        int height = 600;
        pgrfFrame.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pgrfFrame.init(width, height);

    }


    private void init(int width, int height) {
        drawables = new ArrayList<>();
       /* drawables.add(new Nuhelnik(color));
        drawables.get(0).addPoint(new Point(150,150));
        drawables.get(0).addPoint(new Point(100,200));
        drawables.get(0).addPoint(new Point(175,450));
        drawables.get(0).addPoint(new Point(275,320));*/
//        drawables.get(0).addPoint(new Point(300,400));
        fillers = new ArrayList<>();
        // tempScanLine = new ScanLine(renderer,color,drawables.get(0));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Počítačová grafika");
        panel = new JPanel();
        add(panel);


        panel.addMouseMotionListener(this);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (type == DrawableType.LINE) {
                    if (e.getButton() == MouseEvent.BUTTON3 && !firstClick) {
                        firstClick = false;
                    } else if (firstClick) {
                        clickX = e.getX();
                        clickY = e.getY();
                        startX = clickX;
                        startY = clickY;
                        drawables.add(new Line(new Point(clickX, clickY), color));
                        index++;
                        drawables.get(index).count(count);
                        firstClick = !firstClick;
                    } else {
                        clickX = e.getX();
                        clickY = e.getY();
                        drawables.get(index).addPoint(new Point(clickX, clickY));
                        firstClick = !firstClick;
                    }
                }
                if (type == DrawableType.N_OBJECT) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        firstClick = !firstClick;

                    } else {
                        if (firstClick) {

                            clickX = e.getX();
                            clickY = e.getY();
                            startX = clickX;
                            startY = clickY;
                            firstClick = !firstClick;
                            drawables.add(new Nuhelnik(color));
                            index++;
                            drawables.get(index).addPoint(new Point(clickX, clickY));
                        } else {
                            clickX = e.getX();
                            clickY = e.getY();
                            drawables.get(index).addPoint(new Point(clickX, clickY));
                        }
                    }
                }
                if (type == DrawableType.POLYGON) {
                    if (e.getButton() == MouseEvent.BUTTON3 && !firstClick) {
                        firstClick = false;
                    } else if (firstClick) {
                        clickX = e.getX();
                        clickY = e.getY();
                        index += 1;
                        drawables.add(new Polygon(new Point(clickX, clickY), color, renderer));
                        drawables.get(index).count(count);
                        firstClick = !firstClick;
                    } else {
                        clickX = e.getX();
                        clickY = e.getY();
                        firstClick = !firstClick;
                        drawables.get(index).addPoint(new Point(clickX, clickY));
                    }
                } else if (type == DrawableType.CIRCLE) {
                    if (e.getButton() == MouseEvent.BUTTON3 && !firstClick) {
                        firstClick = false;
                    } else if (firstClick) {
                        clickX = e.getX();
                        clickY = e.getY();
                        index += 1;
                        drawables.add(new Circle(color, new Point(clickX, clickY)));
                        drawables.get(index).count(count);
                        firstClick = !firstClick;
                    } else {
                        clickX = e.getX();
                        clickY = e.getY();
                        firstClick = !firstClick;
                        drawables.get(index).addPoint(new Point(clickX, clickY));

                    }
                } else if (type == DrawableType.CIRCLE_SEGMENT) {

                    if (e.getButton() == MouseEvent.BUTTON3 && !firstClick) {
                        circleSegment = new CircleSegment();
                        firstClick = !firstClick;
                    } else if (firstClick) {
                        clickX = e.getX();
                        clickY = e.getY();
                        index += 1;
                        circleSegment.setPoint1(new Point(clickX, clickY));
                        circleSegment.setColor(color);
                        firstClick = !firstClick;
                    } else {
                        clickX = e.getX();
                        clickY = e.getY();
                        firstClick = !firstClick;
                        circleSegment.addPoint(new Point(clickX, clickY));
                        circleSegment.setAlpha(calculateAlpha(circleSegment.getPoint1(), new Point(clickX, clickY)));
                        drawables.add(circleSegment);
                        circleSegment = new CircleSegment();

                    }
                } else if (type == DrawableType.SEED) {
                    seedIndex += 1;
                    fillers.add(new SeedFiller(renderer, color, new Point(e.getX(), e.getY())));

                }
                super.mouseReleased(e);
            }
        });
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                if (type == DrawableType.POLYGON) {

                    if (e.getWheelRotation() < 0) {
                        count++;
                        drawables.get(index).count(count);
                    } else if (e.getWheelRotation() > 0) {
                        if (count == 3) return;
                        count--;
                        drawables.get(index).count(count);
                    }
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_ADD) && type == DrawableType.POLYGON && !firstClick) {
                    count++;
                    drawables.get(index).count(count);
                }
                if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_SUBTRACT) && type == DrawableType.POLYGON && !firstClick) {
                    if (count == 3) return;
                    count--;
                    drawables.get(index).count(count);
                }
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    if (type == DrawableType.LINE) {
                        Message message = new Message();
                        message.upozorneni("Linka už je zvolena");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste linku");
                        type = DrawableType.LINE;
                        firstClick = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    if (type == DrawableType.SEED) {
                        Message message = new Message();
                        message.upozorneni("seed už je zvolen");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste Seed");
                        type = DrawableType.SEED;
                        firstClick = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    if (type == DrawableType.N_OBJECT) {
                        Message message = new Message();
                        message.upozorneni("N-uhelník už je zvolen");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste N-Uhelník");
                        type = DrawableType.N_OBJECT;
                        firstClick = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_K) {
                    if (type == DrawableType.CIRCLE) {
                        Message message = new Message();
                        message.upozorneni("Kružnice už je zvolen");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste Kružnici");
                        type = DrawableType.CIRCLE;
                        firstClick = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    if (type == DrawableType.POLYGON) {
                        Message message = new Message();
                        message.upozorneni("Polygon už je zvolen");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste Polygon");
                        type = DrawableType.POLYGON;
                        firstClick = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    if (type == DrawableType.SCANLINE) {
                        Message message = new Message();
                        message.upozorneni("ScanLine už je zvolen");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste ScanLine");
                        type = DrawableType.SCANLINE;
                        searchIndex = 0;

                        while (drawables.get(searchIndex).getPoints().size() < 3) {
                            searchIndex++;
                        }
                        tempScanLine = new ScanLine(renderer, color, drawables.get(searchIndex));
                        addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT) {


                                    searchIndex++;
                                    if (drawables.size() <= searchIndex) {
                                        searchIndex = 0;
                                    }
                                    if (drawables.get(searchIndex).getPoints().size() < 2) {
                                        searchIndex += 1;
                                    }

                                    tempScanLine = new ScanLine(renderer, color, drawables.get(searchIndex));


                                }

                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                                  /*  System.out.println(drawables.get(0).getPoints().get(0).getX());
                                    System.out.println(drawables.get(0).getPoints().get(0).getY());
*/
                                    fillers.add(new ScanLine(renderer, color, drawables.get(searchIndex)));


                                    seedIndex += 1;
                                }
                            }
                        });
                        firstClick = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_H) {
                    help();
                }
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    Message message = new Message();
                    String s = message.napis();


                    Color newColor = color;
                    try {
                        s = "#" + s;
                        int r = Integer.valueOf(s.substring(1, 3), 16);
                        int g = Integer.valueOf(s.substring(3, 5), 16);
                        int b = Integer.valueOf(s.substring(5, 7), 16);
                        newColor = new Color(r, g, b);

                    } catch (Exception l) {
                        Message message1 = new Message();
                        message1.upozorneni("Špatně zadané číslo");

                    }

                    color = newColor;

                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    drawables = new ArrayList<>();
                    firstClick = true;

                    index = -1;

                    //type = DrawableType.LINE;

                }
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    if (type == DrawableType.CIRCLE_SEGMENT) {
                        Message message = new Message();
                        message.upozorneni("Segment kruhu už je zvolen");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste segment kruhu");
                        type = DrawableType.CIRCLE_SEGMENT;
                        firstClick = true;

                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Message message = new Message();
                    Object[] possibilities = {"Linie", "N-úhelník", "Polygon", "Circle", "Circle Sefment", "Seed", "ScanLine"};
                    String s = message.vyber("Vyberte typ: ", "Výběr typu", possibilities, "Linie");
                    zmenitType(s);
                }
                super.keyReleased(e);
            }
        });


        setLocationRelativeTo(null);
        renderer = new Renderer(img);
        Timer timer = new Timer();
        int FPS = 1000 / 60;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 50, FPS);
    }


    private static void help() {
        Message message = new Message();
        message.upozorneni("Ovládání:\n" +
                "Spacebar: výběr módu(line,Nuhelnik,Polygon,kružnice, segment kružnice)\n" +
                "Klávesa C: změna barvy\n" +
                "Klávesy L,P,N,K,S: přepínání mezi mody linie, polygon ,n-úhelník, kružnice a segment kružnice\n" +
                "U polygonu lze zvýšit počet stran pomocí šipek nahoru a dolů nebo kolečkem myši (+ a - také fungují)\n" +
                "Lze ukončit n-úhelník pomocí pravé klávesy\n" +
                "pokud klikneme pravým tlačítkem na myši před dokončení úsečky, polygonu, kruhu či segmentu, daný objekt se neuloží \n" +
                "Klávesa H: otevře help\n" +
                "Tlačítkem R se resetuje paleta");
    }


    private void draw() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());

        if (!firstClick && type != DrawableType.POLYGON) {
            renderer.drawDashedLine(clickX, clickY, coorX, coorY, color);
            if (type == DrawableType.N_OBJECT)
                renderer.drawDashedLine(startX, startY, coorX, coorY, color);
        }
        if (type == DrawableType.POLYGON && !firstClick)
            renderer.polygon(clickX, clickY, coorX, coorY, count, color);

        if (type == DrawableType.CIRCLE && !firstClick)
            renderer.circle(clickX, clickY, coorX, coorY, color);

        if (type == DrawableType.CIRCLE_SEGMENT && !firstClick)
            renderer.circleSegment(clickX, clickY, coorX, coorY, calculateAlpha(new Point(clickX, clickY), new Point(coorX, coorY)), color);


        for (Drawable drawable : drawables
        ) {
            drawable.draw(renderer);
        }

       /* for (Filler filer:fillers) {
            filer.fill();
        }*/

        for (int i = 0; i <= seedIndex; i++) {
            fillers.get(i).fill();
        }

/*
        for (Filler filler: fillers
             ) {
            filler.fill();
        }
*/


        if (tempScanLine != null)
            tempScanLine.fill(true);

        panel.getGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        panel.paintComponents(getGraphics());

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        coorX = e.getX();
        coorY = e.getY();


    }

    private void zmenitType(String s) {
        if (s != null) {


            switch (s) {

                case "Linie":
                    type = DrawableType.LINE;
                    break;
                case "N-úhelník":
                    type = DrawableType.N_OBJECT;
                    break;
                case "Polygon":
                    type = DrawableType.POLYGON;
                    break;
                case "Circle":
                    type = DrawableType.CIRCLE;
                    break;
                case "Circle Sefment":
                    type = DrawableType.CIRCLE_SEGMENT;
                    break;

                case "Seed":
                    type = DrawableType.SEED;
                    break;
                case "ScanLine":
                    type = DrawableType.SCANLINE;
                    break;
            }
        }

    }


    private double calculateAlpha(Point A, Point B) {

        double c = Vypocty.vypocetVzdalenosti(A.getX(), A.getY(), B.getX(), B.getY());
        boolean over = false;
        Point C = new Point((int) Math.round(A.getX() + c), A.getY());
        double b = Vypocty.vypocetVzdalenosti(A.getX(), A.getY(), C.getX(), C.getY());
        double a = Vypocty.vypocetVzdalenosti(C.getX(), C.getY(), B.getX(), B.getY());
        if (B.getY() > C.getY()) {
            over = true;
        }

        return angle(a, b, c, over);


    }


    private static double angle(double a, double b, double c, boolean over) {

        double ang = Math.acos((a * a - b * b - c * c) / (-2 * b * c));
        if (over) {
            return 360 - Math.toDegrees(ang);
        }
        return Math.toDegrees(ang);
    }


}

