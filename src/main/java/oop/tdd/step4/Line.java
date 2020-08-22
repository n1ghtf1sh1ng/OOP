package oop.tdd.step4;

import java.awt.*;

class Line extends Figure {
    int endX;
    int endY;

    public void setEndXY(int x, int y) {
        this.endX = x;
        this.endY = y;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    @Override
    public void draw(Graphics g) {
        g.drawLine(x, y, endX, endY);
    }
}
