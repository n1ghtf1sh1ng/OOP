package oop.pattern.prototype;

import oop.drawing.moving.Figure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FigureGroupPrototype extends FigurePrototype {
    List<FigurePrototype> figures = new ArrayList<>();

    public FigurePrototype copy() {
        FigureGroupPrototype fig = (FigureGroupPrototype) super.copy();
        //fig.figures == this.figures //this means that the created fig shares list of sub-figures
        fig.figures = new ArrayList<>(); //next, make separate the list of figures
        for (FigurePrototype f : this.figures) {
            fig.figures.add(f.copy());
        }
        return fig;
    }

    public void addFigure(FigurePrototype fig) {
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
