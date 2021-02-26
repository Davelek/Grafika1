package filler;

import utils.Renderer;

import java.awt.*;

public interface Filler {
    void fill();

    int getColor();
    void setColor(Color Color);

    void setRaster(Renderer renderer);
}
