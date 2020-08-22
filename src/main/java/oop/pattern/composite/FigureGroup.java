package oop.pattern.composite;

import oop.drawing.moving.Figure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FigureGroup extends Figure {
    List<Figure> figures = new ArrayList<>();

    public void addFigure(Figure fig) {
        this.figures.add(fig);
    }

    public void move(int dx, int dy) {
        super.move(dx, dy);
        for (Figure f : this.figures) {
            f.move(dx, dy);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        for (Figure f : this.figures) {
            f.draw(g);
        }
        g.setColor(Color.cyan);
        g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public int getWidth() {
        int max = 0;
        for (Figure f : this.figures) {
            max = Math.max(max, f.getX() + f.getWidth());
        }
        return max - this.getX();
    }

    public int getHeight() {
        int max = 0;
        for (Figure f : this.figures) {
            max = Math.max(max, f.getY() + f.getHeight());
        }
        return max - this.getY();
    }
}
