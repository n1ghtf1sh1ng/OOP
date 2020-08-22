package oop.drawing.multiext;

import oop.drawing.moving.Line;

import java.awt.*;

public class MELabeledLine extends Line {
    String text = "line";

    public void setText(String text) {
        this.text = text;
    }

    public void draw(Graphics g) {
        super.draw(g);
        g.drawString(this.text, this.getX(), this.getY() + this.getHeight() + 15);
    }
}
