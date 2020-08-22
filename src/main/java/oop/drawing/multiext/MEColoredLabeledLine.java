package oop.drawing.multiext;

import java.awt.*;

public class MEColoredLabeledLine extends MEColoredLine {
    String text = "Line";

    public void setText(String text) {
        this.text = text;
    }

    public void draw(Graphics g) {
        super.draw(g);
        g.drawString(this.text, this.getX(), this.getY() + this.getHeight() + 15);
    }
}
