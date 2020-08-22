package oop.pattern.visitor;

public abstract class VFigure {
    int x;
    int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void accept(FigureVisitor v);
}
