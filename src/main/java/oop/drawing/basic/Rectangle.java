package oop.drawing.basic;

import java.awt.*;

/**
 * rectangle figure with the size (width,height).
 *  Thus, the bottom right will be (x+width,y+height).
 */
class Rectangle extends Figure {
    int width;
    int height;

    void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    void draw(Graphics g) {
        g.drawRect(this.x, this.y, this.width, this.height);
    }
}
