package oop.drawing.basic;

import java.awt.*;

/**
 * a super type for figures
 */
class Figure {
    /** the left position of the figure */
    int x;

    /** the top position of the figure */
    int y;

    /** move the figure by (dx,dy) */
    void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /** drawing the figure on g: subclasses will overrides the method */
    void draw(Graphics g) {
        //nothing
    }
}
