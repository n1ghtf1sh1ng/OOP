package oop.tdd.step2;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class LineTest2 {
    //// step1

    @Test
    public void testInitXY() {
        Line line = new Line();
        Assert.assertEquals(0, line.getX());
        Assert.assertEquals(0, line.getY());
    }

    @Test
    public void testSetXY() {
        Line line = new Line();
        line.setXY(10, 20);
        Assert.assertEquals(10, line.getX());
        Assert.assertEquals(20, line.getY());
    }

    @Test
    public void testInitEndXY() {
        Line line = new Line();
        Assert.assertEquals(0, line.getEndX());
        Assert.assertEquals(0, line.getEndY());
    }

    @Test
    public void testSetEndXY() {
        Line line = new Line();
        line.setEndXY(10, 20);
        Assert.assertEquals(10, line.getEndX());
        Assert.assertEquals(20, line.getEndY());

        Assert.assertEquals(0, line.getX());
        Assert.assertEquals(0, line.getY());
    }

    //// step2

    @Test
    public void testDraw() {
        Line line = new Line();
        line.setXY(10, 20);
        line.setEndXY(30, 40);
        GraphicsMock g = new GraphicsMock();
        line.draw(g);
        Assert.assertEquals(Collections.singletonList(Arrays.asList(10, 20, 30, 40)), g.lines);
    }
}
