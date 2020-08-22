package oop.pattern.visitor;

import java.io.PrintWriter;

public class FigureSavingVisitor implements FigureVisitor {
    PrintWriter writer;

    public FigureSavingVisitor(PrintWriter writer) {
        this.writer = writer;
    }


    public void visitLine(VLine fig) {
        writer.printf("line:%d,%d,%d,%d\n",
                fig.getX(), fig.getY(), fig.getEndX(), fig.getEndY());
    }

    public void visitRectangle(VRectangle fig) {
        writer.printf("rectangle:%d,%d,%d,%d\n",
                fig.getX(), fig.getY(), fig.getWidth(), fig.getHeight());
    }

    public void visitGroup(VFigureGroup group) {
        writer.printf("group:%d\n", group.getFigures().size());
        for (VFigure f : group.getFigures()) {
            f.accept(this);
        }
        writer.printf("end-group\n");
    }
}
