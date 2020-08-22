package oop.drawing.moving;

import java.awt.*;

public abstract class Figure {
    protected int x;
    protected int y;

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void draw(Graphics g);

    /**
     * @return true if the figure contains the point (tx,ty).
     */
    public boolean contains(int tx, int ty) {
        return this.x <= tx && tx <= this.x + this.getWidth() &&
               this.y <= ty && ty <= this.y + this.getHeight();
    }

    public abstract int getWidth();
    public abstract int getHeight();
}
