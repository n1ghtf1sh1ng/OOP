package oop.tdd.step4;

import java.awt.*;

abstract class Figure {
    //pull up those fields and methods from Line and Rectangle
    int x;
    int y;

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //also the both sub-figures have draw method.
    public abstract void draw(Graphics g);
}
