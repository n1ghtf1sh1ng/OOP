package oop.drawing.multiext;

import oop.drawing.moving.Rectangle;

import java.awt.*;

public class MEColoredRectangle extends Rectangle {
    Color color = Color.red;

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        super.draw(g);
    }
}
