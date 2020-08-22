package oop.pattern.abstractfactory;

import oop.drawing.moving.Figure;
import oop.drawing.moving.Line;
import oop.drawing.moving.Rectangle;
import oop.drawing.multiext.MEColoredLabeledLine;
import oop.drawing.multiext.MEColoredLabeledRectangle;
import oop.drawing.multiext.MEColoredLine;

import java.util.ArrayList;
import java.util.List;

public class FigureFactoryExt extends FigureFactoryDefault {
    static String KIND_EXT_LINE = "Ext Line";
    static String KIND_EXT_RECT = "Ext Rectangle";

    public List<String> getFigureKinds() {
        List<String> ds = new ArrayList<>(super.getFigureKinds()); //creating a copy of array-list of super impl.
        // and adding following 2 new kinds.
        ds.add(KIND_EXT_LINE);
        ds.add(KIND_EXT_RECT);
        return ds;
    }

    public Figure createFigure(String kind) {
        if (kind.equals(KIND_EXT_LINE)) {
            return this.createFigureExtLine();
        } else if (kind.equals(KIND_EXT_RECT)) {
            return this.createFigureExtRectangle();
        } else {
            return super.createFigure(kind);
        }
    }

    /**
     * redefine existing creation for lines
     */
    public Line createFigureLine() {
        Line line = new MEColoredLine();
        line.move(100, 100);
        line.setEndPoint(200, 300);
        return line;
    }

    public Line createFigureExtLine() {
        Line line = new MEColoredLabeledLine();
        line.move(100, 100);
        line.setEndPoint(200, 200);
        return line;
    }

    public Rectangle createFigureExtRectangle() {
        Rectangle rect = new MEColoredLabeledRectangle();
        rect.move(100, 100);
        rect.setSize(100, 100);
        return rect;
    }
}
