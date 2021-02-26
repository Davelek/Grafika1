package masek_PGRF2.controller;

import masek_PGRF2.model.Vertex;
import transforms.Point3D;

import java.awt.*;
import java.util.List;

public class Cube {

    public int count = 36;

    public Cube() {
    }

    public Cube(List<Vertex> vb, List<Integer> ib, int index) {
        vb.add(new Vertex(new Point3D(0, 0, 0), Color.red)); //0

        vb.add(new Vertex(new Point3D(1, 0, 0), Color.red));
        vb.add(new Vertex(new Point3D(0, 1, 0), Color.red));
        vb.add(new Vertex(new Point3D(1, 1, 0), Color.red)); // 3

        vb.add(new Vertex(new Point3D(0, 0, 1), Color.red));
        vb.add(new Vertex(new Point3D(0, 1, 1), Color.red)); // 5

        vb.add(new Vertex(new Point3D(1, 0, 1), Color.orange));
        vb.add(new Vertex(new Point3D(0, 0, 1), Color.orange)); // 7

        vb.add(new Vertex(new Point3D(0, 1, 1), Color.orange));
        vb.add(new Vertex(new Point3D(1, 1, 1), Color.orange)); // 9

        vb.add(new Vertex(new Point3D(1, 1, 1), Color.orange));
        vb.add(new Vertex(new Point3D(1, 0, 1), Color.orange)); // 11

        vb.add(new Vertex(new Point3D(0, 1, 1), Color.orange));
        vb.add(new Vertex(new Point3D(0, 0, 1), Color.orange)); // 13

        ib.add(0 + index);
        ib.add(1 + index);
        ib.add(2 + index);
        ib.add(1 + index);
        ib.add(2 + index);
        ib.add(3 + index);
        ib.add(0 + index);
        ib.add(2 + index);
        ib.add(5 + index);
        ib.add(0 + index);
        ib.add(5 + index);
        ib.add(4 + index);
        ib.add(0 + index);
        ib.add(1 + index);
        ib.add(6 + index);
        ib.add(0 + index);
        ib.add(6 + index);
        ib.add(7 + index);
        ib.add(2 + index);
        ib.add(3 + index);
        ib.add(8 + index);
        ib.add(3 + index);
        ib.add(8 + index);
        ib.add(9 + index);
        ib.add(3 + index);
        ib.add(10 + index);
        ib.add(11 + index);
        ib.add(3 + index);
        ib.add(11 + index);
        ib.add(1 + index);
        ib.add(11 + index);
        ib.add(10 + index);
        ib.add(12 + index);
        ib.add(11 + index);
        ib.add(12 + index);
        ib.add(13 + index);//36
    }
}
