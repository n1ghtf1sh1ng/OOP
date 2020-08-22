package oop.drawing.multiext;

import java.awt.*;

public class MEColoredLabeledRectangle extends MEColoredRectangle {
    String text = "rectangle";

    public void setText(String text) {
        this.text = text;
    }

    public void draw(Graphics g) {
        super.draw(g);
        g.drawString(this.text, this.getX(), this.getY() + this.getHeight() + 15);
    }
}
