package oop.exercise;

import flowcheck.decl.FinderCodeBase;
import flowcheck.decl.FinderMethodDecl;
import flowcheck.decl.FinderPackage;
import flowcheck.decl.FinderTypeDecl;
import oop.drawing.mixin.DebugUtility;
import oop.drawing.moving.Line;
import oop.drawing.moving.Rectangle;
import oop.test.InfolabRunner;
import oop.test.TestUtil;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

@RunWith(InfolabRunner.class)
public class Exercise04MixinTest {
    static FinderCodeBase code;
    @BeforeClass
    public static void setUpClass() {
        code = FinderCodeBase.parseProject().loadFromProject("oop.test.Exercise04Spec");
    }
    @AfterClass
    public static void tearDownClass() {
        code.report();
        code = null;
    }
    @Test public void test01CodeDefineColoring() {
        code.checkPackageByTestName("test01CodeDefineColoring");
    }
    @Test public void test02CodeDefineLabeling() {
        code.checkPackageByTestName("test02CodeDefineLabeling");
    }
    @Test public void test03DefineCodeColoredLabeledLine() throws Exception {
        FinderPackage pack = code.checkPackageByTestName("test03DefineCodeColoredLabeledLine");
        FinderTypeDecl t = pack.findTypeByInspectionName("coloredLabeledLine");

        Line l = (Line) t.toClass()
                .getConstructor()
                .newInstance();

        DebugUtility u = (DebugUtility) l;
        Assert.assertEquals(1, u.incrementDebugCount());
        Assert.assertEquals(2, u.incrementDebugCount());

        FinderMethodDecl getColor = pack.findMethodByInspectionName("getColor");
        FinderMethodDecl getText = pack.findMethodByInspectionName("getText");

        Assert.assertNotNull("set non-null value to the color",
                getColor.invoke(l));
        Assert.assertNotNull("set non-null value to the label",
                getText.invoke(l));
        Assert.assertNotEquals("", getText.invoke(l));
    }

    @Test public void test04CodeDefineColoredLabeledRectangle() throws Exception {
        FinderPackage pack = code.checkPackageByTestName("test04CodeDefineColoredLabeledRectangle");

        FinderTypeDecl t = pack.findTypeByInspectionName("coloredLabeledRectangle");

        Rectangle r = (Rectangle) t.toClass()
                .getConstructor()
                .newInstance();


        DebugUtility u = (DebugUtility) r;
        Assert.assertEquals(1, u.incrementDebugCount());
        Assert.assertEquals(2, u.incrementDebugCount());

        FinderMethodDecl getColor = pack.findMethodByInspectionName("getColor");
        FinderMethodDecl getText = pack.findMethodByInspectionName("getText");

        Assert.assertNotNull("set non-null value to the color",
                getColor.invoke(r));
        Assert.assertNotNull("set non-null value to the label",
                getText.invoke(r));
        Assert.assertNotEquals("", getText.invoke(r));
    }
    @Test public void test05CodeNewExtLineAction() {
        code.checkPackageByTestName("test05CodeNewExtLineAction");
    }
    @Test     public void test05CodeNewExtRectAction() {
        code.checkPackageByTestName("test05CodeNewExtRectAction");
    }

    @Rule
    public Timeout globalTimeout = TestUtil.getTimeout();

}
