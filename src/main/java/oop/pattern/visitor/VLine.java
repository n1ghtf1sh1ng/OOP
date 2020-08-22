package oop.pattern.visitor;

public class VLine extends VFigure {
    int endX;
    int endY;

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndXY(int x, int y) {
        this.endX = x;
        this.endY = y;
    }

    public void accept(FigureVisitor v) {
        v.visitLine(this);
    }
}
