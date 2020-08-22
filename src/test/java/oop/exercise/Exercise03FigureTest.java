package oop.exercise;

import flowcheck.decl.FinderCodeBase;
import oop.drawing.moving.Circle;
import oop.drawing.moving.Line;
import oop.drawing.moving.Rectangle;
import oop.test.InfolabRunner;
import oop.test.TestUtil;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

@RunWith(InfolabRunner.class)
public class Exercise03FigureTest {
    static FinderCodeBase code;
    @BeforeClass
    public static void setUpClass() {
        code = FinderCodeBase.parseProject().loadFromProject("oop.test.Exercise03Spec");
    }
    @AfterClass
    public static void tearDownClass() {
        code.report();
        code = null;
    }
    @Test public void test01CodeLine() throws Exception {
        code.checkPackageByTestName("test01CodeLine");

        Line line = new Line();
        line.move(100, 200);
        line.setEndPoint(200, 350);
        Assert.assertEquals(100, line.getWidth());
        Assert.assertEquals(150, line.getHeight());
    }

    @Test public void test02CodeRectangle() {
        code.checkPackageByTestName("test02CodeRectangle");

        Rectangle rect = new Rectangle();
        rect.setSize(100, 200);
        rect.move(500, 600);
        Assert.assertEquals(100, rect.getWidth());
        Assert.assertEquals(200, rect.getHeight());
    }

    @Test public void test03CodeCircle() {
        code.checkPackageByTestName("test03CodeCircle");

        Circle c = new Circle();
        c.setRadius(123);

        Assert.assertEquals(246, c.getWidth());
        Assert.assertEquals(246, c.getHeight());

    }
    @Test public void test04CodeMousePressed() {
        code.checkPackageByTestName("test04CodeMousePressed");
    }
    @Test public void test05CodeMouseDragged() {
        code.checkPackageByTestName("test05CodeMouseDragged");
    }
    @Test public void test06CodeNewRectAction() {
        code.checkPackageByTestName("test06CodeNewRectAction");
    }
    @Test public void test07CodeNewCircleAction() {
        code.checkPackageByTestName("test07CodeNewCircleAction");
    }

    @Rule
    public Timeout globalTimeout = TestUtil.getTimeout();
}
