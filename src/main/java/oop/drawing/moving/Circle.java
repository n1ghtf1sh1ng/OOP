package oop.drawing.moving;

import java.awt.*;

public class Circle extends Figure {
    int radius;

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getWidth() {
         return 0; //TODO rewrite
    }

    public int getHeight() {
         return 0; //TODO rewrite
    }

    public void draw(Graphics g) {
         //TODO implement
    }

    public String toString() {
        return "Circle(" + this.x + "," + this.y + ", " + this.radius + ")";
    }
}
