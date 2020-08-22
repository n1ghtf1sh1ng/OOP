package oop.drawing.mixin;

import oop.drawing.moving.Line;

import java.awt.*;

public class DebuggingLine extends Line implements DebugUtility {
    int debugCount = 0;

    public int incrementDebugCount() {
        ++this.debugCount;
        return this.debugCount;
    }

    public void draw(Graphics g) {
        super.draw(g);
        DebugUtility.super.log("line draw");
    }
}
