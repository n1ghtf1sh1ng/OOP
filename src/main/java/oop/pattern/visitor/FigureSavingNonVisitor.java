package oop.pattern.visitor;

import java.io.PrintWriter;

public class FigureSavingNonVisitor {
    public void saveLine(VLine fig, PrintWriter writer) {
        writer.printf("line:%d,%d,%d,%d\n",
                fig.getX(), fig.getY(), fig.getEndX(), fig.getEndY());
    }

    public void saveRectangle(VRectangle fig, PrintWriter writer) {
        writer.printf("rectangle:%d,%d,%d,%d\n",
                fig.getX(), fig.getY(), fig.getWidth(), fig.getHeight());
    }

    public void saveGroup(VFigureGroup group, PrintWriter writer) {
        writer.printf("group:%d\n", group.getFigures().size());
        for (VFigure f : group.getFigures()) {
            if (f instanceof VLine) {
                saveLine((VLine) f, writer);
            } else if (f instanceof VRectangle) {
                saveRectangle((VRectangle) f, writer);
            } else if (f instanceof VFigureGroup) {
                saveGroup((VFigureGroup) f, writer);
            }
        }
        writer.printf("end-group\n");
    }
}
