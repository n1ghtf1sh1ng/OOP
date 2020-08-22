package oop.pattern.visitor;

public interface FigureVisitor {
    void visitLine(VLine line);
    void visitRectangle(VRectangle rect);
    void visitGroup(VFigureGroup group);
}
