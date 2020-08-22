package oop.pattern.prototype;

import oop.drawing.moving.Figure;

import java.awt.*;

/** a prototype version of Figure */
public abstract class FigurePrototype extends Figure implements Cloneable {

    public FigurePrototype copy() {
        try {
            return (FigurePrototype) super.clone();
        } catch (CloneNotSupportedException ex) { //never happen
            throw new RuntimeException(ex);
        }
    }
}
