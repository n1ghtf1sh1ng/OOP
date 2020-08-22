package oop.tdd.step3;

import java.awt.*;

/** newly introduced class */
class Rectangle {
    int x;
    int y;
    int width;
    int height;

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

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void draw(Graphics g) {
        g.drawRect(x, y, width, height);
    }
}
