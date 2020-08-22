package oop.pattern.visitor;

import java.awt.*;

public class FigureDrawVisitor implements FigureVisitor {
    Graphics g;

    public FigureDrawVisitor(Graphics g) {
        this.g = g;
    }

    public void visitLine(VLine line) {
        g.drawLine(line.getX(), line.getY(), line.getEndX(), line.getEndY());
    }

    public void visitRectangle(VRectangle rect) {
        g.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public void visitGroup(VFigureGroup group) {
        for (VFigure f : group.getFigures()) {
            f.accept(this);
        }
    }
}
