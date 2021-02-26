package masek_PGRF2.view;

import javax.swing.*;
import java.awt.*;

public class PGRFWindow extends JFrame {

    private final Raster raster;
    private int click;
    private int mousePositionX;
    private int mousePositionY;
    private int newMousePosX;
    private int newMousePosY;
    private Toolbar toolbar;

    public PGRFWindow() {
        // bez tohoto nastavení se okno zavře, ale aplikace stále běží na pozadí
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PGRF2 Mašek David"); // titulek okna

        raster = new Raster();
        raster.setFocusable(true);
        raster.grabFocus(); // důležité pro pozdější ovládání z klávesnice

        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);
        layout.setHgap(0);

        setLayout(layout);
        add(raster); // vložit plátno do okna

        pack();


        setLocationRelativeTo(null); // vycentrovat okno
    }

    public Raster getRaster() {
        return raster;
    }

}
