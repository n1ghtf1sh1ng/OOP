package oop.drawing.composition;

import java.awt.*;

public interface CFigure {
    int getX();
    int getY();
    int getWidth();
    int getHeight();

    void move(int dx, int dy);

    void draw(Graphics g);
}
