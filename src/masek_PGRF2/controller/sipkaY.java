package masek_PGRF2.controller;

import masek_PGRF2.model.Vertex;
import transforms.Mat4RotXYZ;
import transforms.Point3D;

import java.awt.*;
import java.util.List;

public class sipkaY {
    public int count = 18;


    public sipkaY() {
    }

    public sipkaY(java.util.List<Vertex> vb, List<Integer> ib, int index, Color color) {

        vb.add(new Vertex(new Point3D(0, 0, 0), color));
        vb.add(new Vertex(new Point3D(0, 1, 0), color));
        vb.add(new Vertex(new Point3D(0.1, 0.5, 0), color));
        vb.add(new Vertex(new Point3D(-0.1, 0.5, 0), color));
        vb.add(new Vertex(new Point3D(0, 0.5, 0.1), color));
        vb.add(new Vertex(new Point3D(0, 0.5, -0.1), color));


        ib.add(0 + index);
        ib.add(1 + index);
        ib.add(1 + index);
        ib.add(2 + index);
        ib.add(1 + index);
        ib.add(3 + index);
        ib.add(1 + index);
        ib.add(4 + index);
        ib.add(1 + index);
        ib.add(5 + index);
        ib.add(5 + index);
        ib.add(3 + index);
        ib.add(2 + index);
        ib.add(4 + index);
        ib.add(4 + index);
        ib.add(3 + index);
        ib.add(5 + index);
        ib.add(2 + index);

        for (int i = vb.size() - 5; i < vb.size(); i++) {
            vb.get(i).setTransofmable(false);
        }


    }

}
