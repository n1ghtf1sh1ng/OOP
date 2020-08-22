package oop.pattern.abstractfactory;

import oop.drawing.moving.Figure;

import java.util.List;

public interface FigureFactory {
    List<String> getFigureKinds();
    Figure createFigure(String kind);
}
