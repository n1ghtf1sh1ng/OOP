package oop.drawing.composition;

import java.awt.*;

public class CLine implements CFigure {
    int x;
    int y;
    int endX;
    int endY;

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    public int getWidth() {
        return this.endX - this.x;
    }

    public int getHeight() {
        return this.endY - this.y;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        this.endX += dx;
        this.endY += dy;
    }

    public void setEndPoint(int endX, int endY) {
        this.endX = endX;
        this.endY = endY;
    }

    public void draw(Graphics g) {
        g.drawLine(this.x, this.y, this.endX, this.endY);
    }
}
