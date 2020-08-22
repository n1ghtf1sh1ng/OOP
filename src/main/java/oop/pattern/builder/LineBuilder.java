package oop.pattern.builder;

import oop.drawing.moving.Line;
import oop.drawing.multiext.MEColoredLabeledLine;
import oop.drawing.multiext.MEColoredLine;
import oop.drawing.multiext.MELabeledLine;

import java.awt.*;

public class LineBuilder {
    int x;
    int y;
    int endX;
    int endY;
    Color color;
    String text;

    public LineBuilder withXY(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public LineBuilder withEnd(int x, int y) {
        this.endX = x;
        this.endY = y;
        return this;
    }

    public LineBuilder withColor(Color c) {
        this.color = c;
        return this;
    }

    public LineBuilder withLabel(String text) {
        this.text = text;
        return this;
    }

    public Line create() {
        Line l;
        if (this.color != null && this.text != null) {
            var exLine = new MEColoredLabeledLine();
            exLine.setColor(color);
            exLine.setText(this.text);
            l = exLine;
        } else if (this.color != null) {
            var exLine = new MEColoredLine();
            exLine.setColor(color);
            l = exLine;
        } else if (this.text != null) {
            var exLine = new MELabeledLine();
            exLine.setText(this.text);
            l = exLine;
        } else {
            l = new Line();
        }
        l.move(this.x, this.y);
        l.setEndPoint(this.endX, this.endY);
        return l;
    }
}
