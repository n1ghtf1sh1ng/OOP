package oop.drawing.moving;

import java.awt.*;

public class Rectangle extends Figure {
    int width;
    int height;

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public int getWidth() {
         return 0;//TODO rewrite
    }

    public int getHeight() {
         return 0;//TODO rewrite
    }

    public void draw(Graphics g) {
        g.drawRect(this.x, this.y, this.width, this.height);
    }

    public String toString() {
        return "Rectangle(" + this.x + "," + this.y + ", " + this.width + "," + this.height + ")";
    }
}
