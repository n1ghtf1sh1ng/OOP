package oop.drawing.moving;

import java.awt.*;

public class Line extends Figure {
    int endX;
    int endY;


    public void setEndPoint(int x, int y) {
        this.endX = x;
        this.endY = y;
    }

    public void draw(Graphics g) {
        g.drawLine(this.x, this.y, this.endX, this.endY);
    }

    public void move(int dx, int dy) {
        super.move(dx, dy);
        this.endX += dx;
        this.endY += dy;
    }

    public int getWidth() {
         return 0; //TODO rewrite
    }

    public int getHeight() {
         return 0; //TODO rewrite
    }

    public String toString() {
        return "Line((" + this.x + "," + this.y + "), (" + this.endX + "," + this.endY + "))";
    }
}
