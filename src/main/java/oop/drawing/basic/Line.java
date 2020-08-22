package oop.drawing.basic;

import java.awt.*;

/**
 * line figure from (x,y) to (endX,endY)
 */
class Line extends Figure {
    int endX;
    int endY;

    void setEndPoint(int x, int y) {
        this.endX = x;
        this.endY = y;
    }

    void draw(Graphics g) {
        g.drawLine(this.x, this.y, this.endX, this.endY);
    }

    void move(int dx, int dy) {
        super.move(dx, dy);
        this.endX += dx;
        this.endY += dy;
    }
}
