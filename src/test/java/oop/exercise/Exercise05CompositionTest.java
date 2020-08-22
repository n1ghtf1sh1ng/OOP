package oop.exercise;

import flowcheck.decl.*;
import oop.drawing.composition.CLine;
import oop.test.InfolabRunner;
import oop.test.TestUtil;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;

@RunWith(InfolabRunner.class)
public class Exercise05CompositionTest {
    static FinderCodeBase code;
    @BeforeClass
    public static void setUpClass() {
        code = FinderCodeBase.parseProject().loadFromProject("oop.test.Exercise05Spec");
    }
    @AfterClass
    public static void tearDownClass() {
        code.report();
        code = null;
    }
    @Test public void test01CodeColoring() throws Exception {
        FinderPackage pack = code.checkPackageByTestName("test01CodeColoring");
        FinderTypeDecl c = pack.findTypeByInspectionName("coloring");

        FinderMethodDecl getColor = pack.findMethodByInspectionName("getColor");

        Object coloring = c.invokeConstructorDefault();

        Assert.assertNotNull("set non-null value for the color",
                getColor.toMethod()
                .invoke(coloring));
    }

    @Test public void test02CodeColoredLine() {
        code.checkPackageByTestName("test02CodeColoredLine");
    }

    @Test public void test03CodeLabeling() throws Exception {
        FinderPackage pack = code.checkPackageByTestName("test03CodeLabeling");
        FinderTypeDecl l = pack.findTypeByInspectionName("labeling");

        FinderMethodDecl cons = pack.findMethodByInspectionName("init");

        Object labeling = cons.invoke(new CLine());

        FinderFieldDecl text = pack.findFieldByInspectionName("text");

        Object v = text.getFieldValue(labeling);
        Assert.assertNotNull("set non-null value for the text", v);
    }

    @Test public void test04CodeLabeledLine() {
        code.checkPackageByTestName("test04CodeLabeledLine");
    }
    @Test public void test05CodeColoredLabeledLine() {
        code.checkPackageByTestName("test05CodeColoredLabeledLine");
    }
    @Test public void test06CodeEnableSetUp() {
        code.checkPackageByTestName("test06CodeEnableSetUp");
    }
    @Rule
    public Timeout globalTimeout = TestUtil.getTimeout();
}
