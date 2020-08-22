package oop.pattern.abstractfactory;

import oop.drawing.moving.Figure;
import oop.drawing.moving.Line;
import oop.drawing.moving.Rectangle;

import java.util.Arrays;
import java.util.List;

public class FigureFactoryDefault implements FigureFactory {
    static String KIND_LINE = "Line";
    static String KIND_RECT = "Rectangle";

    public List<String> getFigureKinds() {
        return Arrays.asList(KIND_LINE, KIND_RECT);
    }

    public Figure createFigure(String kind) {
        if (kind.equals(KIND_LINE)) {
            return this.createFigureLine();
        } else if (kind.equals(KIND_RECT)) {
            return this.createFigureRectangle();
        } else {
            return null;
        }
    }

    public Line createFigureLine() {
        Line line = new Line();
        line.move(100, 100);
        line.setEndPoint(200, 300);
        return line;
    }

    public Rectangle createFigureRectangle() {
        Rectangle rect = new Rectangle();
        rect.move(100, 100);
        rect.setSize(100, 100);
        return rect;
    }
}
