package oop.drawing.multiext;

import oop.drawing.moving.Line;

import java.awt.*;

public class MEColoredLine extends Line {
    Color color = Color.blue;

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        super.draw(g);
    }
}
