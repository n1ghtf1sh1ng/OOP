package oop.pattern.visitor;

import java.util.ArrayList;
import java.util.List;

public class VFigureGroup extends VFigure {
    List<VFigure> figures = new ArrayList<>();

    public void addFigure(VFigure f) {
        figures.add(f);
    }

    public List<VFigure> getFigures() {
        return figures;
    }

    public void accept(FigureVisitor v) {
        v.visitGroup(this);
    }
}
