package oop.tdd.step3;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class RectangleTest3 {
    //// step3

    ///below 2 methods are same as Line's ones. So, we can refactor in the later step.

    @Test
    public void testInitXY() {
        Rectangle rect = new Rectangle();
        Assert.assertEquals(0, rect.getX());
        Assert.assertEquals(0, rect.getY());
    }

    @Test
    public void testSetXY() {
        Rectangle rect = new Rectangle();
        rect.setXY(10, 20);
        Assert.assertEquals(10, rect.getX());
        Assert.assertEquals(20, rect.getY());
    }

    @Test
    public void testInitSize() {
        Rectangle rect = new Rectangle();
        Assert.assertEquals(0, rect.getWidth());
        Assert.assertEquals(0, rect.getHeight());
    }

    @Test
    public void testSetSize() {
        Rectangle rect = new Rectangle();
        rect.setSize(10, 20);
        Assert.assertEquals(10, rect.getWidth());
        Assert.assertEquals(20, rect.getHeight());

        Assert.assertEquals(0, rect.getX());
        Assert.assertEquals(0, rect.getY());
    }


    @Test
    public void testDraw() {
        Rectangle rect = new Rectangle();
        rect.setXY(10, 20);
        rect.setSize(30, 40);
        GraphicsMock g = new GraphicsMock();
        rect.draw(g);
        Assert.assertEquals(
                new HashSet<>(Arrays.asList( //we don't care about order of line-elements
                        Arrays.asList(10, 60, 10, 21), //bottom-left to top-left
                        Arrays.asList(10, 20, 39, 20), //top-left to top-right
                        Arrays.asList(40, 20, 40, 59),  //top-right to bottom-right
                        Arrays.asList(40, 60, 11, 60))), //bottom-right to bottom-left
                new HashSet<>(g.lines));
    }
}
