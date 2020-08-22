package oop.drawing.multiext;

import oop.drawing.moving.Rectangle;

import java.awt.*;

public class MELabeledRectangle extends Rectangle {
    String text = "rectangle";

    public void setText(String text) {
        this.text = text;
    }

    public void draw(Graphics g) {
        super.draw(g);
        g.drawString(this.text, this.getX(), this.getY() + this.getHeight() + 15);
    }
}
