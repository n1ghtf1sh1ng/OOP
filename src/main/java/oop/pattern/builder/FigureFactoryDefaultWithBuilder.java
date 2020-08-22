package oop.pattern.builder;

import oop.drawing.moving.Line;
import oop.pattern.abstractfactory.FigureFactoryDefault;

import java.awt.*;

public class FigureFactoryDefaultWithBuilder extends FigureFactoryDefault { //the Builder pattern can be used with the Abstract Factory pattern
    LineBuilder lineBuilder = new LineBuilder();

    public FigureFactoryDefaultWithBuilder() {
        this.lineBuilder
                .withXY(100, 100)
                .withEnd(200, 200)
                .withColor(Color.blue)
                .withLabel("Line");
    }

    public Line createFigureLine() {
        return this.lineBuilder.create();
    }
}
