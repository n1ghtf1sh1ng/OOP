package oop.pattern.prototype;

import oop.drawing.moving.Line;

import java.awt.*;

/**
 * redefinition of Line with supporting copying:
 *   the class reuse the super-implementation of copy() as is.
 */
public class LinePrototype extends FigurePrototype {
    int endX;
    int endY;

    /** In current Java, we can change the return type of overriding method as
     *     a narrowed (more specific) type. */
    public LinePrototype copy() {
        return (LinePrototype) super.copy();
    }

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
        return this.endX - this.x;
    }

    public int getHeight() {
        return this.endY - this.y;
    }

    public String toString() {
        return "Line((" + this.x + "," + this.y + "), (" + this.endX + "," + this.endY + "))";
    }

}
