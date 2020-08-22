package oop.pattern.visitor;

public class VRectangle extends VFigure {
    int width;
    int height;

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void accept(FigureVisitor v) {
        v.visitRectangle(this);
    }
}
