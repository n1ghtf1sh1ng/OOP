package oop.tdd.step1;

import org.junit.Assert;
import org.junit.Test;

public class LineTest1 {
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
}
