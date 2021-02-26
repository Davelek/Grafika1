package masek_PGRF2.controller;

import masek_PGRF2.model.Vertex;
import transforms.Point3D;

import java.awt.*;
import java.util.List;

public class Jehlan {
    int count = 18;

    public Jehlan() {
    }

    public Jehlan(List<Vertex> vb, List<Integer> ib, int index) {
        vb.add(new Vertex(new Point3D(0, 0, 1), Color.yellow)); //0
        vb.add(new Vertex(new Point3D(-1, -1, 0), Color.red)); //0
        vb.add(new Vertex(new Point3D(-1, 1, 0), Color.red)); //0
        vb.add(new Vertex(new Point3D(1, 1, 0), Color.red)); //0
        vb.add(new Vertex(new Point3D(1, -1, 0), Color.red)); //0

        ib.add(0 + index);
        ib.add(1 + index);
        ib.add(2 + index);
        ib.add(0 + index);
        ib.add(1 + index);
        ib.add(4 + index);
        ib.add(0 + index);
        ib.add(4 + index);
        ib.add(3 + index);
        ib.add(0 + index);
        ib.add(2 + index);
        ib.add(3 + index);
        ib.add(1 + index);
        ib.add(3 + index);
        ib.add(2 + index);
        ib.add(1 + index);
        ib.add(4 + index);
        ib.add(3 + index);

    }
}
